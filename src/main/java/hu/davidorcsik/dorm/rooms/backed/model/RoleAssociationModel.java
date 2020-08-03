package hu.davidorcsik.dorm.rooms.backed.model;

import hu.davidorcsik.dorm.rooms.backed.database.RoleConnectorRepo;
import hu.davidorcsik.dorm.rooms.backed.entity.People;
import hu.davidorcsik.dorm.rooms.backed.entity.Role;
import hu.davidorcsik.dorm.rooms.backed.entity.RoleConnector;
import hu.davidorcsik.dorm.rooms.backed.status.RoleAssociationRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public RoleAssociationRequestStatus associate(People people, Role role) {
        Optional<RoleConnector> rc = roleConnectorRepo.findByPeopleAndRole(people, role);
        if (rc.isPresent()) return RoleAssociationRequestStatus.ROLE_ALREADY_ASSOCIATED;

        roleConnectorRepo.save(new RoleConnector(people, role));
        return RoleAssociationRequestStatus.OK;
    }

    public RoleAssociationRequestStatus disassociate(People people, Role role) {
        Optional<RoleConnector> rc = roleConnectorRepo.findByPeopleAndRole(people, role);
        if (rc.isEmpty()) return RoleAssociationRequestStatus.ROLE_NOT_ASSOCIATED;

        roleConnectorRepo.delete(rc.get());
        return RoleAssociationRequestStatus.OK;
    }
}
