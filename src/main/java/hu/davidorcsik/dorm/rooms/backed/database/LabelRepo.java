package hu.davidorcsik.dorm.rooms.backed.database;

import hu.davidorcsik.dorm.rooms.backed.entity.Label;
import org.springframework.data.repository.CrudRepository;

public interface LabelRepo extends CrudRepository<Label, Long> {
    boolean existsByName(String name);
}
