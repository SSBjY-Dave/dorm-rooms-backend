package hu.davidorcsik.dorm.rooms.backed.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import hu.davidorcsik.dorm.rooms.backed.security.ResponseView;
import lombok.*;
import org.springframework.data.annotation.ReadOnlyProperty;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "room_connector")
public class RoomConnector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ResponseView.PublicView.class)
    private long id;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "people_id", referencedColumnName = "id")
    @ToString.Exclude
    @ReadOnlyProperty
    @JsonView(ResponseView.PublicView.class)
    private People people;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    @ToString.Exclude
    @JsonView(ResponseView.PublicView.class)
    private Room room;

    public RoomConnector(People people, Room room) {
        this.people = people;
        this.room = room;
    }

    public People getPeople() {
        return people;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) { this.room = room; }

    public void removeRoom() {
        room = null;
    }

    public void prepareSerialization() {
        people.prepareSerializationFromRoomConnector();
        room.prepareSerializationFromRoomConnector();
    }

    void prepareSerializationFromPeople() {
        people = null;
        room.prepareSerializationFromRoomConnector();
    }

    void prepareSerializationFromRoom() {
        room = null;
        people.prepareSerializationFromRoomConnector();
    }
}
