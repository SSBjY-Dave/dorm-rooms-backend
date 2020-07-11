package hu.davidorcsik.dorm.rooms.backed.types;

import hu.davidorcsik.dorm.rooms.backed.entity.Room;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class RoomModificationData {
    private Room room;
    private Sex sex;
    private boolean locked;

    public Room getRoom() {
        return room;
    }

    public Sex getSex() {
        return sex;
    }

    public boolean isLocked() {
        return locked;
    }
}
