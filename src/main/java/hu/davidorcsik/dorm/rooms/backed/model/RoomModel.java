package hu.davidorcsik.dorm.rooms.backed.model;

import hu.davidorcsik.dorm.rooms.backed.database.RoomRepo;
import hu.davidorcsik.dorm.rooms.backed.entity.Room;
import hu.davidorcsik.dorm.rooms.backed.status.RoomRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class RoomModel {
    private static RoomModel instance;

    public static RoomModel getInstance() {
        return instance;
    }

    private final RoomRepo roomRepo;

    @Autowired
    public RoomModel(RoomRepo roomRepo) {
        assert(instance == null);
        this.roomRepo = roomRepo;
        instance = this;
    }

    public Optional<Room> getDatabaseObject(Room r) {
        return roomRepo.findById(r.getId());
    }

    public ArrayList<RoomRequestStatus> modify(Room r) {
        ArrayList<RoomRequestStatus> status = Room.isRoomValid(r);
        if (!status.isEmpty()) return status;

        if (!roomRepo.existsById(r.getId())) status.add(RoomRequestStatus.ID_DOES_NOT_EXISTS);
        if (!status.isEmpty()) return status;

        roomRepo.save(r);
        status.add(RoomRequestStatus.OK);
        return status;
    }
}
