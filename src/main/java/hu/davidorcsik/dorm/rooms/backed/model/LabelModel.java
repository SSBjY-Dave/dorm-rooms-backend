package hu.davidorcsik.dorm.rooms.backed.model;

import hu.davidorcsik.dorm.rooms.backed.database.LabelRepo;
import hu.davidorcsik.dorm.rooms.backed.entity.Label;
import hu.davidorcsik.dorm.rooms.backed.status.LabelRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LabelModel {
    private static LabelModel instance;

    public static LabelModel getInstance() {
        return instance;
    }

    private final LabelRepo labelRepo;

    @Autowired
    public LabelModel(LabelRepo labelRepo) {
        this.labelRepo = labelRepo;
        instance = this;
    }

    public ArrayList<LabelRequestStatus> add(Label l) {
        ArrayList<LabelRequestStatus> status = new ArrayList<>(Label.isLabelValid(l));
        if (!status.isEmpty()) return status;

        if (labelRepo.existsById(l.getId())) status.add(LabelRequestStatus.ID_ALREADY_EXISTS);
        if (labelRepo.existsByName(l.getName())) status.add(LabelRequestStatus.NAME_ALREADY_EXISTS);
        if (!status.isEmpty()) return status;

        labelRepo.save(l);
        status.add(LabelRequestStatus.OK);
        return status;
    }

    public ArrayList<LabelRequestStatus> delete(Label l) {
        ArrayList<LabelRequestStatus> status = new ArrayList<>();

        if (!labelRepo.existsById(l.getId())) status.add(LabelRequestStatus.ID_DOES_NOT_EXISTS);
        if (!labelRepo.existsByName(l.getName())) status.add(LabelRequestStatus.NAME_DOES_NOT_EXISTS);
        if (!status.isEmpty()) return status;

        labelRepo.save(l);
        status.add(LabelRequestStatus.OK);
        return status;
    }

    public ArrayList<LabelRequestStatus> modify(Label l) {
        ArrayList<LabelRequestStatus> status = new ArrayList<>(Label.isLabelValid(l));
        if (!status.isEmpty()) return status;

        if (!labelRepo.existsById(l.getId())) status.add(LabelRequestStatus.ID_DOES_NOT_EXISTS);
        if (!labelRepo.existsByName(l.getName())) status.add(LabelRequestStatus.NAME_DOES_NOT_EXISTS);
        if (!status.isEmpty()) return status;

        labelRepo.delete(l);
        status.add(LabelRequestStatus.OK);
        return status;
    }

    public ArrayList<Label> getAll() {
        ArrayList<Label> labels = new ArrayList<>();
        labelRepo.findAll().forEach(labels::add);
        return labels;
    }
}
