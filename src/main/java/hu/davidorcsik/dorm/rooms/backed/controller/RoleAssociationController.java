package hu.davidorcsik.dorm.rooms.backed.controller;

import hu.davidorcsik.dorm.rooms.backed.entity.People;
import hu.davidorcsik.dorm.rooms.backed.entity.Role;
import hu.davidorcsik.dorm.rooms.backed.model.PeopleModel;
import hu.davidorcsik.dorm.rooms.backed.model.RoleAssociationModel;
import hu.davidorcsik.dorm.rooms.backed.model.RoleModel;
import hu.davidorcsik.dorm.rooms.backed.status.RoleAssociationRequestStatus;
import hu.davidorcsik.dorm.rooms.backed.types.RoleAssociationData;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class RoleAssociationController {
    @PostMapping("/roleAssociation/associate")
    public RoleAssociationRequestStatus associate(@RequestBody RoleAssociationData rad) {
        Optional<People> people = PeopleModel.getInstance().getDatabaseEntity(rad.getPeople());
        if (people.isEmpty()) return RoleAssociationRequestStatus.PEOPLE_INVALID;

        Optional<Role> role = RoleModel.getInstance().getDatabaseEntityByType(rad.getRoleType());
        if (role.isEmpty()) return RoleAssociationRequestStatus.ROLE_INVALID;

        return RoleAssociationModel.getInstance().associate(people.get(), role.get());
    }

    @PostMapping("/roleAssociation/disassociate")
    public RoleAssociationRequestStatus disassociate(@RequestBody RoleAssociationData rad) {
        Optional<People> people = PeopleModel.getInstance().getDatabaseEntity(rad.getPeople());
        if (people.isEmpty()) return RoleAssociationRequestStatus.PEOPLE_INVALID;

        Optional<Role> role = RoleModel.getInstance().getDatabaseEntityByType(rad.getRoleType());
        if (role.isEmpty()) return RoleAssociationRequestStatus.ROLE_INVALID;

        return RoleAssociationModel.getInstance().disassociate(people.get(), role.get());
    }
}
