package hu.davidorcsik.dorm.rooms.backed.controller;

import hu.davidorcsik.dorm.rooms.backed.entity.People;
import hu.davidorcsik.dorm.rooms.backed.model.PeopleModel;
import hu.davidorcsik.dorm.rooms.backed.status.PeopleRequestStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class PeopleController {
    @PostMapping("/people/add")
    public PeopleRequestStatus[] add(@RequestBody People p) {
        return PeopleModel.getInstance().add(p).toArray(new PeopleRequestStatus[0]);
    }

    @PostMapping("/people/delete")
    public PeopleRequestStatus[] delete(@RequestBody People p) {
        return PeopleModel.getInstance().delete(p).toArray(new PeopleRequestStatus[0]);
    }

    @PostMapping("/people/modify")
    public PeopleRequestStatus[] modify(@RequestBody People p) {
        return PeopleModel.getInstance().modify(p).toArray(new PeopleRequestStatus[0]);
    }

    @GetMapping("/people/getAll")
    public People[] getAll() {
        return PeopleModel.getInstance().getAll().toArray(new People[0]);
    }

    //TODO: create a mapping similar to getAll but it MUST NOT include
    // a person's id, token, neptun and email, but keep it on controller level

    //TODO: all mapping in this controller is for the admin, they must be protected from normal users
}
