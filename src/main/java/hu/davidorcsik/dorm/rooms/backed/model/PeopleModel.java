package hu.davidorcsik.dorm.rooms.backed.model;

import hu.davidorcsik.dorm.rooms.backed.entity.People;
import hu.davidorcsik.dorm.rooms.backed.status.PeopleDataStatus;

import java.util.ArrayList;

public class PeopleModel {
    public static ArrayList<PeopleDataStatus> add(People p) {
        ArrayList<PeopleDataStatus> status = new ArrayList<>(People.isPeopleValid(p));
        if (!status.isEmpty()) return status;

        status.add(PeopleDataStatus.OK);
        return status;
    }
}
