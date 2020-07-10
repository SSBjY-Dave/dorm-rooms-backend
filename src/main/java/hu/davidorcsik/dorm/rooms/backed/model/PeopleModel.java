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
        this.peopleRepo = peopleRepo;
        instance = this;
    }

    public Optional<People> getDatabaseObejct(People p) {
        return peopleRepo.findById(p.getId());
    }

    public ArrayList<PeopleRequestStatus> add(People p) {
        ArrayList<PeopleRequestStatus> status = People.isPeopleValid(p);
        if (!status.isEmpty()) return status;

        if (peopleRepo.existsById(p.getId())) status.add(PeopleRequestStatus.ID_ALREADY_EXISTS);
        if (peopleRepo.existsByNeptunId(p.getNeptunId())) status.add(PeopleRequestStatus.NEPTUN_ID_ALREADY_EXISTS);
        if (peopleRepo.existsByEmail(p.getEmail())) status.add(PeopleRequestStatus.EMAIL_ALREADY_EXSITS);
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
        //TODO: Delete reservation and label connection
        peopleRepo.delete(p);
        status.add(PeopleRequestStatus.OK);
        return status;
    }

    public ArrayList<PeopleRequestStatus> modify(People p) {
        ArrayList<PeopleRequestStatus> status = People.isPeopleValid(p);
        if (!status.isEmpty()) return status;

        if (!peopleRepo.existsById(p.getId())) status.add(PeopleRequestStatus.ID_INVALID);
        //TODO: Check if data already in use
        if (!status.isEmpty()) return status;

        peopleRepo.save(p); //TODO: more security or something
        status.add(PeopleRequestStatus.OK);
        return status;
    }

    public ArrayList<People> getAll() {
        ArrayList<People> people = new ArrayList<>();
        peopleRepo.findAll().forEach(people::add);
        return people;
    }
}
