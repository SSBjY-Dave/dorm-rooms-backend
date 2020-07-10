package hu.davidorcsik.dorm.rooms.backed.model;

import hu.davidorcsik.dorm.rooms.backed.database.PeopleRepo;
import hu.davidorcsik.dorm.rooms.backed.database.RoomConnectorRepo;
import hu.davidorcsik.dorm.rooms.backed.database.RoomRepo;
import hu.davidorcsik.dorm.rooms.backed.entity.People;
import hu.davidorcsik.dorm.rooms.backed.entity.Room;
import hu.davidorcsik.dorm.rooms.backed.entity.RoomConnector;
import hu.davidorcsik.dorm.rooms.backed.status.ReservationRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    public ReservationModel(PeopleRepo peopleRepo, RoomRepo roomRepo, RoomConnectorRepo roomConnectorRepo) {
        this.peopleRepo = peopleRepo;
        this.roomRepo = roomRepo;
        this.roomConnectorRepo = roomConnectorRepo;
        instance = this;
    }

    public ReservationRequestStatus applyForRoom(People people, Room room) {
        //TODO: check room sex
        return assignToRoom(people, room);
    }

    public ReservationRequestStatus leaveRoom(People people) {
        Optional<RoomConnector> rc = roomConnectorRepo.findByPeople(people);
        if (rc.isEmpty()) return ReservationRequestStatus.RESERVATION_NOT_FOUND;
        roomConnectorRepo.delete(rc.get());
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
        if (rc.isEmpty()) return ReservationRequestStatus.RESERVATION_NOT_FOUND;

        ReservationRequestStatus status = assignToRoom(people, room);
        if (!status.equals(ReservationRequestStatus.OK)) return status;

        roomConnectorRepo.delete(rc.get());
        return ReservationRequestStatus.OK;
    }

    public ReservationRequestStatus assignToRoom(People people, Room room) {
        if (!peopleRepo.existsById(people.getId())) return ReservationRequestStatus.PEOPLE_ID_INVALID;
        Optional<RoomConnector> rc = roomConnectorRepo.findByPeople(people);
        if (rc.isPresent()) return ReservationRequestStatus.RESERVATION_ALREADY_EXISTS;

        if (!roomRepo.existsById(room.getId())) return ReservationRequestStatus.ROOM_ID_INVALID;
        Iterable<RoomConnector> rcs = roomConnectorRepo.findAllByRoom(room);
        if (room.isFull()) return ReservationRequestStatus.ROOM_ALREADY_FULL;

        RoomConnector reservation = new RoomConnector(people, room);
        roomConnectorRepo.save(reservation);
        return ReservationRequestStatus.OK;
    }
}
