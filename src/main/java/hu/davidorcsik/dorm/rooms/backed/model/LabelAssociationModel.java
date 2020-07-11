package hu.davidorcsik.dorm.rooms.backed.model;

import hu.davidorcsik.dorm.rooms.backed.database.LabelConnectorRepo;
import hu.davidorcsik.dorm.rooms.backed.entity.Label;
import hu.davidorcsik.dorm.rooms.backed.entity.LabelConnector;
import hu.davidorcsik.dorm.rooms.backed.entity.People;
import hu.davidorcsik.dorm.rooms.backed.status.LabelAssociationRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LabelAssociationModel {
    private static LabelAssociationModel instance;

    public static LabelAssociationModel getInstance() {
        return instance;
    }

    private final LabelConnectorRepo labelConnectorRepo;

    @Autowired
    public LabelAssociationModel(LabelConnectorRepo labelConnectorRepo) {
        assert(instance == null);
        this.labelConnectorRepo = labelConnectorRepo;
        instance = this;
    }

    public LabelAssociationRequestStatus disassociate(People p, Label l) {
        Optional<LabelConnector> lc = labelConnectorRepo.findByPeopleAndLabel(p, l);
        if (lc.isEmpty()) return LabelAssociationRequestStatus.LABEL_IS_NOT_ASSIGNED;
        labelConnectorRepo.delete(lc.get());
        return LabelAssociationRequestStatus.OK;
    }

    public LabelAssociationRequestStatus assign(People p, Label l) {
        Optional<LabelConnector> lc = labelConnectorRepo.findByPeopleAndLabel(p, l);
        if (lc.isPresent()) return LabelAssociationRequestStatus.LABEL_ALREADY_ASSIGNED;
        labelConnectorRepo.save(new LabelConnector(p, l));
        return LabelAssociationRequestStatus.OK;
    }
}
