package hu.davidorcsik.dorm.rooms.backed.controller;

import hu.davidorcsik.dorm.rooms.backed.entity.People;
import hu.davidorcsik.dorm.rooms.backed.entity.Room;
import hu.davidorcsik.dorm.rooms.backed.model.RoomModel;
import hu.davidorcsik.dorm.rooms.backed.security.DormRoomsUserDetailsService;
import hu.davidorcsik.dorm.rooms.backed.status.RoomRequestStatus;
import hu.davidorcsik.dorm.rooms.backed.types.RoomModificationData;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:4200") //TODO: Modify for production server
public class RoomController {
    @PostMapping("/room/setLockState")
    public RoomRequestStatus setLockState(@RequestBody RoomModificationData rmd) {
        Optional<Room> r = RoomModel.getInstance().getDatabaseEntity(rmd.getRoom());
        if (r.isEmpty()) return RoomRequestStatus.ID_INVALID;
        return RoomModel.getInstance().setLockState(r.get(), rmd.isLocked());
    }

    @PostMapping("/room/setAllowedSex")
    public RoomRequestStatus setAllowedSex(@RequestBody RoomModificationData rmd) {
        Room room;
        if (DormRoomsUserDetailsService.isCurrentUserResident()) {
            People people = DormRoomsUserDetailsService.getCurrentUser();
            if (people.getRoomConnector() == null) return RoomRequestStatus.ROOM_NUMBER_DOES_NOT_EXISTS;
            room = people.getRoomConnector().getRoom();
        } else {
            Optional<Room> r = RoomModel.getInstance().getDatabaseEntity(rmd.getRoom());
            if (r.isEmpty()) return RoomRequestStatus.ID_INVALID;
            room = r.get();
        }
        return RoomModel.getInstance().setAllowedSex(room, rmd.getSex());
    }

    @GetMapping("/room/getAll")
    public Room[] getAll() {
        return RoomModel.getInstance().getAll().toArray(new Room[0]);
    }
}
