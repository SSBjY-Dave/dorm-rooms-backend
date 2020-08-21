package hu.davidorcsik.dorm.rooms.backed.controller;

import hu.davidorcsik.dorm.rooms.backed.entity.People;
import hu.davidorcsik.dorm.rooms.backed.entity.Room;
import hu.davidorcsik.dorm.rooms.backed.model.PeopleModel;
import hu.davidorcsik.dorm.rooms.backed.model.ReservationModel;
import hu.davidorcsik.dorm.rooms.backed.model.RoomModel;
import hu.davidorcsik.dorm.rooms.backed.security.DormRoomsUserDetailsService;
import hu.davidorcsik.dorm.rooms.backed.status.ReservationRequestStatus;
import hu.davidorcsik.dorm.rooms.backed.types.ReservationData;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://localhost:80", "http://localhost:4200"})
public class ReservationController {
    @PostMapping("/reservation/applyForRoom")
    public ReservationRequestStatus applyForRoom(@RequestBody Room r) {
        People people = DormRoomsUserDetailsService.getCurrentUser();
        Optional<Room> room = RoomModel.getInstance().getDatabaseEntity(r);
        if (room.isEmpty()) return ReservationRequestStatus.ROOM_ID_INVALID;

        return ReservationModel.getInstance().applyForRoom(people, room.get());
    }

    @PostMapping("/reservation/leaveRoom")
    public ReservationRequestStatus leaveRoom() {
        return ReservationModel.getInstance().leaveRoom(DormRoomsUserDetailsService.getCurrentUser());
    }

    @PostMapping("/reservation/changeRoom")
    public ReservationRequestStatus changeRoom(@RequestBody Room r) {
        People people = DormRoomsUserDetailsService.getCurrentUser();
        Optional<Room> room = RoomModel.getInstance().getDatabaseEntity(r);
        if (room.isEmpty()) return ReservationRequestStatus.ROOM_ID_INVALID;

        return ReservationModel.getInstance().changeRoom(people, room.get());
    }

    @PostMapping("/reservation/assignToRoom")
    public ReservationRequestStatus assignToRoom(@RequestBody ReservationData rd) {
        Optional<People> p = PeopleModel.getInstance().getDatabaseEntity(rd.getPeople());
        if (p.isEmpty()) return ReservationRequestStatus.PEOPLE_ID_INVALID;

        Optional<Room> r = RoomModel.getInstance().getDatabaseEntity(rd.getRoom());
        if (r.isEmpty()) return ReservationRequestStatus.ROOM_ID_INVALID;

        return ReservationModel.getInstance().assignToRoom(p.get(), r.get());
    }

    @PostMapping("/reservation/clearRoom")
    public ReservationRequestStatus clearRoom(@RequestBody Room r) {
        Optional<Room> room = RoomModel.getInstance().getDatabaseEntity(r);
        if (room.isEmpty()) return ReservationRequestStatus.ROOM_ID_INVALID;

        return ReservationModel.getInstance().clearRoom(room.get());
    }
}
