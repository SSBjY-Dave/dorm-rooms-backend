package hu.davidorcsik.dorm.rooms.backed.controller;

import hu.davidorcsik.dorm.rooms.backed.entity.Label;
import hu.davidorcsik.dorm.rooms.backed.entity.People;
import hu.davidorcsik.dorm.rooms.backed.model.LabelAssociationModel;
import hu.davidorcsik.dorm.rooms.backed.model.LabelModel;
import hu.davidorcsik.dorm.rooms.backed.model.PeopleModel;
import hu.davidorcsik.dorm.rooms.backed.status.LabelAssociationRequestStatus;
import hu.davidorcsik.dorm.rooms.backed.types.LabelAssociationData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class LabelAssociationController {
    @PostMapping("/labelAssociation/disassociate")
    public LabelAssociationRequestStatus disassociate(LabelAssociationData lad) {
        Optional<People> p = PeopleModel.getInstance().getDatabaseObejct(lad.getPeople());
        if (p.isEmpty()) return LabelAssociationRequestStatus.PEOPLE_ID_INVALID;

        Optional<Label> l = LabelModel.getInstance().getDatabaseObject(lad.getLabel());
        if (l.isEmpty()) return LabelAssociationRequestStatus.LABEL_ID_INVALID;

        return LabelAssociationModel.getInstance().disassociate(p.get(), l.get());
    }

    @PostMapping("/labelAssociation/associate")
    public LabelAssociationRequestStatus associate(LabelAssociationData lad) {
        Optional<People> p = PeopleModel.getInstance().getDatabaseObejct(lad.getPeople());
        if (p.isEmpty()) return LabelAssociationRequestStatus.PEOPLE_ID_INVALID;

        Optional<Label> l = LabelModel.getInstance().getDatabaseObject(lad.getLabel());
        if (l.isEmpty()) return LabelAssociationRequestStatus.LABEL_ID_INVALID;

        return LabelAssociationModel.getInstance().assign(p.get(), l.get());
    }

    //TODO: all mapping in this controller is for the admin, they must be protected from normal users
}
