package hu.davidorcsik.dorm.rooms.backed.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RoomConnector {
    private long people_id;
    private long room_id;

    public long getPeople_id() {
        return people_id;
    }

    public long getRoom_id() {
        return room_id;
    }
}
