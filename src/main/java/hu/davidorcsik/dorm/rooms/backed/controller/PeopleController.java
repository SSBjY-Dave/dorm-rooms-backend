package hu.davidorcsik.dorm.rooms.backed.controller;

import hu.davidorcsik.dorm.rooms.backed.entity.People;
import hu.davidorcsik.dorm.rooms.backed.model.PeopleModel;
import hu.davidorcsik.dorm.rooms.backed.status.PeopleDataStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PeopleController {
    @PostMapping("/people/add")
    public PeopleDataStatus[] add(@RequestBody People p) {
        return PeopleModel.add(p).toArray(new PeopleDataStatus[0]);
    }
}
