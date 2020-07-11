package hu.davidorcsik.dorm.rooms.backed.model;

import hu.davidorcsik.dorm.rooms.backed.database.LabelConnectorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//FIXME: how should we call this shit? LabelerModel doesn't sound too good (to rename everywhere use shift + f6)
@Service
public class LabelAllocatorModel {
    private static LabelAllocatorModel instance;

    public static LabelAllocatorModel getInstance() {
        return instance;
    }

    private final LabelConnectorRepo labelConnectorRepo;

    @Autowired
    public LabelAllocatorModel(LabelConnectorRepo labelConnectorRepo) {
        assert(instance == null);
        this.labelConnectorRepo = labelConnectorRepo;
        instance = this;
    }
}
