package hu.davidorcsik.dorm.rooms.backed.database;

import hu.davidorcsik.dorm.rooms.backed.entity.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepo extends CrudRepository<Role, Long> {
    Optional<Role> findByRole(Role.Type roleType);
}
