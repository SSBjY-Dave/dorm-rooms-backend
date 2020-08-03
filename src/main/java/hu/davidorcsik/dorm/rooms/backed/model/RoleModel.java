package hu.davidorcsik.dorm.rooms.backed.model;

import hu.davidorcsik.dorm.rooms.backed.database.RoleRepo;
import hu.davidorcsik.dorm.rooms.backed.entity.Role;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleModel {
    private static RoleModel instance;

    public static RoleModel getInstance() {
        return instance;
    }

    private final RoleRepo roleRepo;

    public RoleModel(RoleRepo roleRepo) {
        assert(instance == null);
        this.roleRepo = roleRepo;
        instance = this;
    }

    public Optional<Role> getDatabaseEntityByType(Role.Type roleType) {
        return roleRepo.findByRole(roleType);
    }
}
