package hu.davidorcsik.dorm.rooms.backed.database;

import hu.davidorcsik.dorm.rooms.backed.entity.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepo extends CrudRepository<Room, Long> {

}
