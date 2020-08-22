package hu.davidorcsik.dorm.rooms.backed.controller;

import hu.davidorcsik.dorm.rooms.backed.entity.*;
import hu.davidorcsik.dorm.rooms.backed.model.RoomModel;
import hu.davidorcsik.dorm.rooms.backed.security.DormRoomsUserDetailsService;
import hu.davidorcsik.dorm.rooms.backed.status.RoomRequestStatus;
import hu.davidorcsik.dorm.rooms.backed.types.RoomModificationData;
import hu.davidorcsik.dorm.rooms.backed.types.Sex;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;

@RestController
@CrossOrigin(origins = "*")
public class RoomController {
    @PostMapping("/room/setLockState")
    public RoomRequestStatus setLockState(@RequestBody RoomModificationData rmd) {
        Optional<Room> r = RoomModel.getInstance().getDatabaseEntity(rmd.getRoom());
        if (r.isEmpty()) return RoomRequestStatus.ID_INVALID;
        return RoomModel.getInstance().setLockState(r.get(), rmd.isLocked());
    }

    @PostMapping("/room/setAllowedSex")
    public RoomRequestStatus setAllowedSex(@RequestBody RoomModificationData rmd) {
        BiFunction<Room, Sex, RoomRequestStatus> setAllowedSexFunction;
        AtomicReference<Room> room = new AtomicReference<>();

        if (DormRoomsUserDetailsService.isCurrentUserAdmin()) {
            setAllowedSexFunction = RoomModel.getInstance()::setAllowedSexUnsafe;
            RoomModel.getInstance().getDatabaseEntity(rmd.getRoom()).ifPresent(room::set);
        } else {
            setAllowedSexFunction = RoomModel.getInstance()::setAllowedSexSafe;
            room.set(DormRoomsUserDetailsService.getCurrentUser().getRoomConnector().getRoom());
        }

        if (room.get() == null) return RoomRequestStatus.ID_INVALID;

        return setAllowedSexFunction.apply(room.get(), rmd.getSex());
    }

    @GetMapping("/room/getAll")
    public Room[] getAll() {
        List<Room> rooms = RoomModel.getInstance().getAll();
        rooms.forEach(Room::prepareSerialization);
        return rooms.toArray(new Room[0]);
    }
}
