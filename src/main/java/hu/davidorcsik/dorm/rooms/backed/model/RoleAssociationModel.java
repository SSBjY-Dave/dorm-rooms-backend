package hu.davidorcsik.dorm.rooms.backed.model;

import hu.davidorcsik.dorm.rooms.backed.database.RoleConnectorRepo;
import hu.davidorcsik.dorm.rooms.backed.entity.People;
import hu.davidorcsik.dorm.rooms.backed.entity.Role;
import hu.davidorcsik.dorm.rooms.backed.entity.RoleConnector;
import hu.davidorcsik.dorm.rooms.backed.status.RoleAssiciationRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleAssociationModel {
    private static RoleAssociationModel instance;

    public static RoleAssociationModel getInstance() {
        return instance;
    }

    private final RoleConnectorRepo roleConnectorRepo;

    @Autowired
    public RoleAssociationModel(RoleConnectorRepo roleConnectorRepo) {
        assert(instance == null);
        this.roleConnectorRepo = roleConnectorRepo;
        instance = this;
    }

    public RoleAssiciationRequestStatus associate(People people, Role role) {
        Optional<RoleConnector> rc = roleConnectorRepo.findByPeopleAndRole(people, role);
        if (rc.isPresent()) return RoleAssiciationRequestStatus.ROLE_ALREADY_ASSOCIATED;

        roleConnectorRepo.save(new RoleConnector(people, role));
        return RoleAssiciationRequestStatus.OK;
    }

    public RoleAssiciationRequestStatus disassociate(People people, Role role) {
        Optional<RoleConnector> rc = roleConnectorRepo.findByPeopleAndRole(people, role);
        if (rc.isEmpty()) return RoleAssiciationRequestStatus.ROLE_NOT_ASSOCIATED;

        roleConnectorRepo.delete(rc.get());
        return RoleAssiciationRequestStatus.OK;
    }
}
