package hu.davidorcsik.dorm.rooms.backed.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import hu.davidorcsik.dorm.rooms.backed.status.RoomRequestStatus;
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
    //TODO: for expandability reasons the capacity should be part of the database prototype
    // please somebody do it I cannot be bothered :D
    public static int CAPACITY = 2;
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

    public boolean isFull() {
        return roomConnectors.size() >= CAPACITY;
    }

    public boolean isOverfilled() {
        return roomConnectors.size() > CAPACITY;
    }
}
