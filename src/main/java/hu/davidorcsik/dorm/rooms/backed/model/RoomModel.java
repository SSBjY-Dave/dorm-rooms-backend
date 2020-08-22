package hu.davidorcsik.dorm.rooms.backed.model;

import hu.davidorcsik.dorm.rooms.backed.database.RoomRepo;
import hu.davidorcsik.dorm.rooms.backed.entity.People;
import hu.davidorcsik.dorm.rooms.backed.entity.Room;
import hu.davidorcsik.dorm.rooms.backed.status.RoomRequestStatus;
import hu.davidorcsik.dorm.rooms.backed.types.Sex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    public Optional<Room> getDatabaseEntity(Room r) {
        return roomRepo.findById(r.getId());
    }

    public RoomRequestStatus setLockState(Room r, boolean lockState) {
        r.setLocked(lockState);
        roomRepo.save(r);
        return RoomRequestStatus.OK;
    }

    public RoomRequestStatus setAllowedSexSafe(Room r, Sex sex) {
        List<People> residents = r.getResidents();
        if (!sex.equals(Sex.ANY) && residents.stream().anyMatch(p -> !p.getSex().equals(sex)))
            return RoomRequestStatus.SEX_INVALID;
        return setAllowedSexUnsafe(r, sex);
    }

    public RoomRequestStatus setAllowedSexUnsafe(Room r, Sex sex) {
        r.setSex(sex);
        roomRepo.save(r);
        return RoomRequestStatus.OK;
    }

    public ArrayList<Room> getAll() {
        ArrayList<Room> rooms = new ArrayList<>();
        roomRepo.findAll().forEach(rooms::add);
        return rooms;
    }
}
