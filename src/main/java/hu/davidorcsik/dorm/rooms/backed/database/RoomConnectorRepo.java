package hu.davidorcsik.dorm.rooms.backed.database;

import hu.davidorcsik.dorm.rooms.backed.entity.People;
import hu.davidorcsik.dorm.rooms.backed.entity.Room;
import hu.davidorcsik.dorm.rooms.backed.entity.RoomConnector;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoomConnectorRepo extends CrudRepository<RoomConnector, Long> {
    Optional<RoomConnector> findByPeople(People p);
    Iterable<RoomConnector> findAllByRoom(Room r);
    Long countByRoom(Room r);
}
