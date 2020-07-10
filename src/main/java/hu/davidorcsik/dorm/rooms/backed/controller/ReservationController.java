package hu.davidorcsik.dorm.rooms.backed.controller;

import hu.davidorcsik.dorm.rooms.backed.entity.People;
import hu.davidorcsik.dorm.rooms.backed.entity.Room;
import hu.davidorcsik.dorm.rooms.backed.model.PeopleModel;
import hu.davidorcsik.dorm.rooms.backed.model.ReservationModel;
import hu.davidorcsik.dorm.rooms.backed.model.RoomModel;
import hu.davidorcsik.dorm.rooms.backed.status.ReservationRequestStatus;
import hu.davidorcsik.dorm.rooms.backed.types.ReservationData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ReservationController {

    @PostMapping("/reservation/applyForRoom")
    public ReservationRequestStatus applyForRoom(@RequestBody ReservationData rd) {
        Optional<People> p = PeopleModel.getInstance().getDatabaseObejct(rd.getPeople());
        if (p.isEmpty()) return ReservationRequestStatus.PEOPLE_ID_INVALID;

        Optional<Room> r = RoomModel.getInstance().getDatabaseObject(rd.getRoom());
        if (r.isEmpty()) return ReservationRequestStatus.ROOM_ID_INVALID;

        return ReservationModel.getInstance().applyForRoom(p.get(), r.get());
    }

    @PostMapping("/reservation/leaveRoom")
    public ReservationRequestStatus leaveRoom(@RequestBody ReservationData rd) {
        Optional<People> p = PeopleModel.getInstance().getDatabaseObejct(rd.getPeople());
        if (p.isEmpty()) return ReservationRequestStatus.PEOPLE_ID_INVALID;

        return ReservationModel.getInstance().leaveRoom(p.get());
    }

    @PostMapping("/reservation/changeRoom")
    public ReservationRequestStatus changeRoom(@RequestBody ReservationData rd) {
        Optional<People> p = PeopleModel.getInstance().getDatabaseObejct(rd.getPeople());
        if (p.isEmpty()) return ReservationRequestStatus.PEOPLE_ID_INVALID;

        Optional<Room> r = RoomModel.getInstance().getDatabaseObject(rd.getRoom());
        if (r.isEmpty()) return ReservationRequestStatus.ROOM_ID_INVALID;

        return ReservationModel.getInstance().changeRoom(p.get(), r.get());
    }

    @PostMapping("/reservation/assignToRoom")
    public ReservationRequestStatus assignToRoom(@RequestBody ReservationData rd) {
        Optional<People> p = PeopleModel.getInstance().getDatabaseObejct(rd.getPeople());
        if (p.isEmpty()) return ReservationRequestStatus.PEOPLE_ID_INVALID;

        Optional<Room> r = RoomModel.getInstance().getDatabaseObject(rd.getRoom());
        if (r.isEmpty()) return ReservationRequestStatus.ROOM_ID_INVALID;

        //TODO: security
        return ReservationModel.getInstance().assignToRoom(p.get(), r.get());
    }

    @PostMapping("/reservation/clearRoom")
    public ReservationRequestStatus clearRoom(@RequestBody ReservationData rd) {
        Optional<Room> r = RoomModel.getInstance().getDatabaseObject(rd.getRoom());
        if (r.isEmpty()) return ReservationRequestStatus.ROOM_ID_INVALID;

        //TODO: security
        return ReservationModel.getInstance().clearRoom(r.get());
    }

    //TODO: applyForRoom - transaction
    //TODO: changeRoom - transaction
    //TODO: assignToRoom - transaction, administrative function, do not care about sex
}
