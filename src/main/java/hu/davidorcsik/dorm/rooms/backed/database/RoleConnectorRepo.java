package hu.davidorcsik.dorm.rooms.backed.database;

import hu.davidorcsik.dorm.rooms.backed.entity.RoleConnector;
import org.springframework.data.repository.CrudRepository;

public interface RoleConnectorRepo extends CrudRepository<RoleConnector, Long> {
}
