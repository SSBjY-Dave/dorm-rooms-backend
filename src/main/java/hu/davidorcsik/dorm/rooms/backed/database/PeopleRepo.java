package hu.davidorcsik.dorm.rooms.backed.database;

import hu.davidorcsik.dorm.rooms.backed.entity.People;
import org.springframework.data.repository.CrudRepository;

public interface PeopleRepo extends CrudRepository<People, Long> {
    boolean existsByNeptunId(String neptunId);
    boolean existsByEmail(String email);
    boolean existsByToken(String token);
}
