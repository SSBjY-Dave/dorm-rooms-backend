package hu.davidorcsik.dorm.rooms.backed.database;

import hu.davidorcsik.dorm.rooms.backed.entity.LabelConnector;
import org.springframework.data.repository.CrudRepository;

public interface LabelConnectorRepo extends CrudRepository<LabelConnector, Long> {
}
