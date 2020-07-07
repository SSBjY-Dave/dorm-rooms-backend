package hu.davidorcsik.dorm.rooms.backed.controller;

import hu.davidorcsik.dorm.rooms.backed.entity.Room;
import hu.davidorcsik.dorm.rooms.backed.model.RoomModel;
import hu.davidorcsik.dorm.rooms.backed.status.RoomRequestStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomController {
    @PostMapping("/room/modify")
    public RoomRequestStatus[] modify(@RequestBody Room r) {
        return RoomModel.getInstance().modify(r).toArray(new RoomRequestStatus[0]);
    }
}
