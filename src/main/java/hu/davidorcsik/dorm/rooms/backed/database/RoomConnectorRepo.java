package hu.davidorcsik.dorm.rooms.backed.database;

import hu.davidorcsik.dorm.rooms.backed.entity.RoomConnector;
import org.springframework.data.repository.CrudRepository;

public interface RoomConnectorRepo extends CrudRepository<RoomConnector, Long> {
}
