package hu.davidorcsik.dorm.rooms.backed.model;

import hu.davidorcsik.dorm.rooms.backed.database.PeopleRepo;
import hu.davidorcsik.dorm.rooms.backed.database.RoomConnectorRepo;
import hu.davidorcsik.dorm.rooms.backed.database.RoomRepo;
import hu.davidorcsik.dorm.rooms.backed.entity.People;
import hu.davidorcsik.dorm.rooms.backed.entity.Room;
import hu.davidorcsik.dorm.rooms.backed.entity.RoomConnector;
import hu.davidorcsik.dorm.rooms.backed.status.ReservationRequestStatus;
import hu.davidorcsik.dorm.rooms.backed.types.Sex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ReservationModel {
    private static ReservationModel instance;

    public static ReservationModel getInstance() {
        return instance;
    }

    private final PeopleRepo peopleRepo;
    private final RoomRepo roomRepo;
    private final RoomConnectorRepo roomConnectorRepo;
    private static final Object reservationSynchronizationObject = new Object();

    @Autowired
    public ReservationModel(PeopleRepo peopleRepo, RoomRepo roomRepo, RoomConnectorRepo roomConnectorRepo) {
        assert(instance == null);
        this.peopleRepo = peopleRepo;
        this.roomRepo = roomRepo;
        this.roomConnectorRepo = roomConnectorRepo;
        instance = this;
    }

    public ReservationRequestStatus applyForRoom(People people, Room room) {
        if (room.isLocked()) return ReservationRequestStatus.ROOM_IS_LOCKED;
        if (room.getSex() != Sex.ANY && !people.getSex().equals(room.getSex())) return ReservationRequestStatus.SEX_INVALID;
        return assignToRoom(people, room);
    }

    public ReservationRequestStatus leaveRoom(People people) {
        Optional<RoomConnector> rc = roomConnectorRepo.findByPeople(people);
        if (rc.isEmpty()) return ReservationRequestStatus.RESERVATION_NOT_FOUND;
        Room oldRoom = rc.get().getRoom();
        roomConnectorRepo.delete(rc.get());

        oldRoom = RoomModel.getInstance().getDatabaseEntity(oldRoom).get();
        if (oldRoom.getRoomConnectors().size() == 0) RoomModel.getInstance().setAllowedSexUnsafe(oldRoom, Sex.ANY);
        return ReservationRequestStatus.OK;
    }

    public ReservationRequestStatus clearRoom(Room room) {
        Iterable<RoomConnector> rcs = roomConnectorRepo.findAllByRoom(room);
        for (RoomConnector rc : rcs) {
            roomConnectorRepo.delete(rc);
        }
        return ReservationRequestStatus.OK;
    }

    public ReservationRequestStatus changeRoom(People people, Room room) {
        Optional<RoomConnector> rc = roomConnectorRepo.findByPeople(people);
        Room oldRoom = people.getRoomConnector().getRoom();
        if (rc.isEmpty()) return ReservationRequestStatus.RESERVATION_NOT_FOUND;

        RoomConnector reservation = rc.get();
        reservation.setRoom(room);

        try {
            changeReservation(reservation);
        } catch (IllegalStateException e) {
            return ReservationRequestStatus.DATA_RACE_LOST;
        }

        oldRoom = RoomModel.getInstance().getDatabaseEntity(oldRoom).get();
        if (oldRoom.getRoomConnectors().size() == 0) RoomModel.getInstance().setAllowedSexUnsafe(oldRoom, Sex.ANY);

        return ReservationRequestStatus.OK;
    }

    @Transactional(rollbackFor = Exception.class)
    protected void changeReservation(RoomConnector reservation) {
        Room currentRoom = roomConnectorRepo.findByPeople(reservation.getPeople()).get().getRoom();

        Room newRoom = roomRepo.findById(reservation.getRoom().getId()).get();
        long reservationCountForRoom = roomConnectorRepo.countByRoom(reservation.getRoom());
        if (reservationCountForRoom >= newRoom.getCapacity()) throw new IllegalStateException("Data race lost");

        roomConnectorRepo.save(reservation);
        //TODO: this synchronization should be avoided and the database engine should take care of it.
        // we should find a way to do it in spring but for now it'll do. however it can be very slow if there a lot of
        // simulations reservation request (not limited to one room queue)
        synchronized (reservationSynchronizationObject) {
            newRoom = roomRepo.findById(reservation.getRoom().getId()).get();
            if (newRoom.isOverfilled()) throw new IllegalStateException("Data race lost");
        }
    }

    public ReservationRequestStatus assignToRoom(People people, Room room) {
        if (!peopleRepo.existsById(people.getId())) return ReservationRequestStatus.PEOPLE_ID_INVALID;

        if (!roomRepo.existsById(room.getId())) return ReservationRequestStatus.ROOM_ID_INVALID;
        if (room.isFull()) return ReservationRequestStatus.ROOM_ALREADY_FULL;

        try {
            addReservation(new RoomConnector(people, room));
        } catch (IllegalStateException e) {
            return ReservationRequestStatus.DATA_RACE_LOST;
        }

        return ReservationRequestStatus.OK;
    }

    @Transactional(rollbackFor = Exception.class)
    protected void addReservation(RoomConnector reservation) {
        Room r = roomRepo.findById(reservation.getRoom().getId()).get();
        if (r.isOverfilled()) throw new IllegalStateException("Data race lost");
        //TODO: change room if the person is already in one

        roomConnectorRepo.findByPeople(reservation.getPeople()).ifPresent(roomConnectorRepo::delete);
        roomConnectorRepo.save(reservation);
        //TODO: this synchronization should be avoided and the database engine should take care of it.
        // we should find a way to do it in spring but for now it'll do. however it can be very slow if there a lot of
        // simulations reservation request (not limited to one room queue)
        synchronized (reservationSynchronizationObject) {
            r = roomRepo.findById(reservation.getRoom().getId()).get();
            if (r.isOverfilled()) throw new IllegalStateException("Data race lost");
        }
    }
}
