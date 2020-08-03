package hu.davidorcsik.dorm.rooms.backed.database;

import hu.davidorcsik.dorm.rooms.backed.entity.People;
import hu.davidorcsik.dorm.rooms.backed.entity.Role;
import hu.davidorcsik.dorm.rooms.backed.entity.RoleConnector;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleConnectorRepo extends CrudRepository<RoleConnector, Long> {
    Optional<RoleConnector> findByPeopleAndRole(People p, Role r);
}
