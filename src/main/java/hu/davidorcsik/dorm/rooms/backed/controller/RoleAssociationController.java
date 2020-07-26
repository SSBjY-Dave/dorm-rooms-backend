package hu.davidorcsik.dorm.rooms.backed.controller;

import hu.davidorcsik.dorm.rooms.backed.entity.People;
import hu.davidorcsik.dorm.rooms.backed.entity.Role;
import hu.davidorcsik.dorm.rooms.backed.model.PeopleModel;
import hu.davidorcsik.dorm.rooms.backed.model.RoleAssociationModel;
import hu.davidorcsik.dorm.rooms.backed.model.RoleModel;
import hu.davidorcsik.dorm.rooms.backed.status.RoleAssiciationRequestStatus;
import hu.davidorcsik.dorm.rooms.backed.types.RoleAssociationData;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:4200") //TODO: Modify for production server
public class RoleAssociationController {
    @PostMapping("/roleAssociation/associate")
    public RoleAssiciationRequestStatus associate(RoleAssociationData rad) {
        Optional<People> people = PeopleModel.getInstance().getDatabaseEntity(rad.getPeople());
        if (people.isEmpty()) return RoleAssiciationRequestStatus.PEOPLE_INVALID;

        Optional<Role> role = RoleModel.getInstance().getDatabaseEntityByType(rad.getRoleType());
        if (role.isEmpty()) return RoleAssiciationRequestStatus.ROLE_INVALID;

        return RoleAssociationModel.getInstance().associate(people.get(), role.get());
    }

    @PostMapping("/roleAssociation/disassociate")
    public RoleAssiciationRequestStatus disassociate(RoleAssociationData rad) {
        Optional<People> people = PeopleModel.getInstance().getDatabaseEntity(rad.getPeople());
        if (people.isEmpty()) return RoleAssiciationRequestStatus.PEOPLE_INVALID;

        Optional<Role> role = RoleModel.getInstance().getDatabaseEntityByType(rad.getRoleType());
        if (role.isEmpty()) return RoleAssiciationRequestStatus.ROLE_INVALID;

        return RoleAssociationModel.getInstance().disassociate(people.get(), role.get());
    }
}
