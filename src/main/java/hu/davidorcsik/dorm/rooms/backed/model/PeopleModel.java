package hu.davidorcsik.dorm.rooms.backed.model;

import hu.davidorcsik.dorm.rooms.backed.database.PeopleRepo;
import hu.davidorcsik.dorm.rooms.backed.entity.People;
import hu.davidorcsik.dorm.rooms.backed.status.PeopleRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class PeopleModel {
    private static PeopleModel instance;

    public static PeopleModel getInstance() {
        return instance;
    }

    private final PeopleRepo peopleRepo;

    @Autowired
    public PeopleModel(PeopleRepo peopleRepo) {
        assert(instance == null);
        this.peopleRepo = peopleRepo;
        instance = this;
    }

    public Optional<People> getDatabaseEntity(People p) {
        Optional<People> dbEntity = peopleRepo.findById(p.getId());
        dbEntity.ifPresent(people -> people.setNeptunId(people.getNeptunId().toUpperCase()));
        return dbEntity;
    }
    public Optional<People> getDatabaseEntityByNeptunId(String neptunId) {
        Optional<People> dbEntity = peopleRepo.findByNeptunId(neptunId.toUpperCase());
        dbEntity.ifPresent(people -> people.setNeptunId(people.getNeptunId().toUpperCase()));
        return dbEntity;
    }

    public ArrayList<PeopleRequestStatus> add(People p) {
        ArrayList<PeopleRequestStatus> status = People.isPeopleValid(p);
        if (!status.isEmpty()) return status;
        p.setNeptunId(p.getNeptunId().toUpperCase());

        if (peopleRepo.existsById(p.getId())) status.add(PeopleRequestStatus.ID_ALREADY_EXISTS);
        if (peopleRepo.existsByNeptunId(p.getNeptunId())) status.add(PeopleRequestStatus.NEPTUN_ID_ALREADY_EXISTS);
        if (peopleRepo.existsByEmail(p.getEmail())) status.add(PeopleRequestStatus.EMAIL_ALREADY_EXITS);
        if (peopleRepo.existsByToken(p.getToken())) status.add(PeopleRequestStatus.TOKEN_ALREADY_EXISTS);
        if (!status.isEmpty()) return status;

        peopleRepo.save(p);
        status.add(PeopleRequestStatus.OK);
        return status;
    }

    public ArrayList<PeopleRequestStatus> delete(People p) {
        ArrayList<PeopleRequestStatus> status = new ArrayList<>();

        if (!peopleRepo.existsById(p.getId())) status.add(PeopleRequestStatus.ID_INVALID);
        if (!peopleRepo.existsByNeptunId(p.getNeptunId())) status.add(PeopleRequestStatus.NEPTUN_ID_INVALID);
        if (!peopleRepo.existsByEmail(p.getEmail())) status.add(PeopleRequestStatus.EMAIL_INVALID);
        if (!peopleRepo.existsByToken(p.getToken())) status.add(PeopleRequestStatus.TOKEN_INVALID);
        if (!status.isEmpty()) return status;

        ReservationModel.getInstance().leaveRoom(p);
        LabelAssociationModel.getInstance().disassociateAll(p);
        peopleRepo.delete(p);
        status.add(PeopleRequestStatus.OK);
        return status;
    }

    public ArrayList<PeopleRequestStatus> modify(People p) {
        ArrayList<PeopleRequestStatus> status = People.isPeopleValid(p);
        if (!status.isEmpty()) return status;
        p.setNeptunId(p.getNeptunId().toUpperCase());

        People databaseEntity = getDatabaseEntity(p).orElse(null);

        if (databaseEntity == null) {
            status.add(PeopleRequestStatus.ID_INVALID);
            return status;
        }

        if (!databaseEntity.getNeptunId().equals(p.getNeptunId()) && peopleRepo.existsByNeptunId(p.getNeptunId()))
            status.add(PeopleRequestStatus.NEPTUN_ID_ALREADY_EXISTS);
        if (!databaseEntity.getEmail().equals(p.getEmail()) && peopleRepo.existsByEmail(p.getEmail()))
            status.add(PeopleRequestStatus.EMAIL_ALREADY_EXITS);
        if (!status.isEmpty()) return status;

        p.setToken(databaseEntity.getToken());
        p.setRoomConnector(databaseEntity.getRoomConnector());
        p.setRoleConnector(databaseEntity.getRoleConnectors());
        p.setLabelConnectors(databaseEntity.getLabelConnectors());

        peopleRepo.save(p);
        status.add(PeopleRequestStatus.OK);
        return status;
    }

    public ArrayList<People> getAll() {
        ArrayList<People> people = new ArrayList<>();
        peopleRepo.findAll().forEach(people::add);
        return people;
    }
}
