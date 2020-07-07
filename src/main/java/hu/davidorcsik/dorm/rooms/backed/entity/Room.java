package hu.davidorcsik.dorm.rooms.backed.entity;

import hu.davidorcsik.dorm.rooms.backed.status.RoomRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;

@Entity
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    public static boolean isIdValid(long id, int level, int roomNumber) {
        return id == level * 100 + roomNumber;
    }

    public static boolean isLevelValid(int level) {
        return level >= 0;
    }

    public static boolean isRoomNumberValid(int roomNumber) {
        return roomNumber > 0;
    }

    public static ArrayList<RoomRequestStatus> isRoomValid(Room r) {
        ArrayList<RoomRequestStatus> status = new ArrayList<>();

        if (!isIdValid(r.getId(), r.getLevel(), r.getRoomNumber())) status.add(RoomRequestStatus.ID_INVALID);
        if (!isLevelValid(r.getLevel())) status.add(RoomRequestStatus.LEVEL_INVALID);
        if (!isRoomNumberValid(r.getRoomNumber())) status.add(RoomRequestStatus.ROOM_NUMBER_INVALID);

        return status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int level;
    private int roomNumber;
    private boolean locked;

    public long getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        recalculateId();
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
        recalculateId();
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    private void recalculateId() {
        this.id = level * 100 + roomNumber;
    }
}
