package hu.davidorcsik.dorm.rooms.backed.database;

import hu.davidorcsik.dorm.rooms.backed.entity.Label;
import hu.davidorcsik.dorm.rooms.backed.entity.LabelConnector;
import hu.davidorcsik.dorm.rooms.backed.entity.People;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LabelConnectorRepo extends CrudRepository<LabelConnector, Long> {
    Optional<LabelConnector> findByPeopleAndLabel(People p, Label l);
    boolean existsByPeopleAndLabel(People p, Label l);
}
