package hu.davidorcsik.dorm.rooms.backed.database;

import hu.davidorcsik.dorm.rooms.backed.entity.People;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PeopleRepo extends CrudRepository<People, Long> {
    boolean existsByNeptunId(String neptunId);
    boolean existsByEmail(String email);
    Optional<People> findByNeptunId(String neptunId);
    boolean existsByToken(String token);
}
