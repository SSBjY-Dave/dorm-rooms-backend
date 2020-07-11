package hu.davidorcsik.dorm.rooms.backed.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import hu.davidorcsik.dorm.rooms.backed.status.RoomRequestStatus;
import hu.davidorcsik.dorm.rooms.backed.types.Sex;
import lombok.*;
import org.springframework.data.annotation.ReadOnlyProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rooms")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
    @ReadOnlyProperty
    private long id;
    @ReadOnlyProperty
    private int level;
    @ReadOnlyProperty
    private int roomNumber;
    private boolean locked;
    @ReadOnlyProperty
    private int capacity;
    @Enumerated(value = EnumType.ORDINAL)
    private Sex sex;
    @OneToMany(mappedBy = "room")
    @ReadOnlyProperty
    @ToString.Exclude
    private List<RoomConnector> roomConnectors;

    public long getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public boolean isFull() {
        return roomConnectors.size() >= capacity;
    }

    public boolean isOverfilled() {
        return roomConnectors.size() > capacity;
    }
}
