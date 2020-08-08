package hu.davidorcsik.dorm.rooms.backed.controller;

import hu.davidorcsik.dorm.rooms.backed.DormRoomsBackendApplication;
import hu.davidorcsik.dorm.rooms.backed.entity.Label;
import hu.davidorcsik.dorm.rooms.backed.entity.People;
import hu.davidorcsik.dorm.rooms.backed.model.LabelAssociationModel;
import hu.davidorcsik.dorm.rooms.backed.model.LabelModel;
import hu.davidorcsik.dorm.rooms.backed.model.PeopleModel;
import hu.davidorcsik.dorm.rooms.backed.security.SecurityConfigurationAdapter;
import hu.davidorcsik.dorm.rooms.backed.status.LabelAssociationRequestStatus;
import hu.davidorcsik.dorm.rooms.backed.types.LabelAssociationData;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@CrossOrigin(origins = { "http://localhost:4200", "http://89.134.96.61:4200"}) //TODO: Modify for production server
public class LabelAssociationController {
    @PostMapping("/labelAssociation/disassociate")
    public LabelAssociationRequestStatus disassociate(LabelAssociationData lad) {
        Optional<People> p = PeopleModel.getInstance().getDatabaseEntity(lad.getPeople());
        if (p.isEmpty()) return LabelAssociationRequestStatus.PEOPLE_ID_INVALID;

        Optional<Label> l = LabelModel.getInstance().getDatabaseEntity(lad.getLabel());
        if (l.isEmpty()) return LabelAssociationRequestStatus.LABEL_ID_INVALID;

        return LabelAssociationModel.getInstance().disassociate(p.get(), l.get());
    }

    @PostMapping("/labelAssociation/associate")
    public LabelAssociationRequestStatus associate(LabelAssociationData lad) {
        Optional<People> p = PeopleModel.getInstance().getDatabaseEntity(lad.getPeople());
        if (p.isEmpty()) return LabelAssociationRequestStatus.PEOPLE_ID_INVALID;

        Optional<Label> l = LabelModel.getInstance().getDatabaseEntity(lad.getLabel());
        if (l.isEmpty()) return LabelAssociationRequestStatus.LABEL_ID_INVALID;

        return LabelAssociationModel.getInstance().assign(p.get(), l.get());
    }
}
