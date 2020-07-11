package hu.davidorcsik.dorm.rooms.backed.controller;

import hu.davidorcsik.dorm.rooms.backed.entity.Room;
import hu.davidorcsik.dorm.rooms.backed.model.RoomModel;
import hu.davidorcsik.dorm.rooms.backed.status.RoomRequestStatus;
import hu.davidorcsik.dorm.rooms.backed.types.RoomModificationData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class RoomController {
    @PostMapping("/room/setLockState")
    public RoomRequestStatus setLockState(@RequestBody RoomModificationData rmd) {
        Optional<Room> r = RoomModel.getInstance().getDatabaseEntity(rmd.getRoom());
        if (r.isEmpty()) return RoomRequestStatus.ID_INVALID;
        //TODO: only admin should be able to use this mapping
        return RoomModel.getInstance().setLockState(r.get(), rmd.isLocked());
    }

    @PostMapping("/room/setAllowedSex")
    public RoomRequestStatus setAllowedSex(@RequestBody RoomModificationData rmd) {
        Optional<Room> r = RoomModel.getInstance().getDatabaseEntity(rmd.getRoom());
        if (r.isEmpty()) return RoomRequestStatus.ID_INVALID;
        //TODO: only admin and a person in the room should be able to use this mapping
        return RoomModel.getInstance().setAllowedSex(r.get(), rmd.getSex());
    }
}
