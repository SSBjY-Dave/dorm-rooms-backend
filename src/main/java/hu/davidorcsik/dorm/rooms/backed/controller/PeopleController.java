package hu.davidorcsik.dorm.rooms.backed.controller;

import com.fasterxml.jackson.annotation.JsonView;
import hu.davidorcsik.dorm.rooms.backed.entity.People;
import hu.davidorcsik.dorm.rooms.backed.model.PeopleModel;
import hu.davidorcsik.dorm.rooms.backed.security.DormRoomsUserDetailsService;
import hu.davidorcsik.dorm.rooms.backed.security.ResponseView;
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
    @JsonView(ResponseView.PublicView.class)
    public People[] getAll() {
        return PeopleModel.getInstance().getAll().toArray(new People[0]);
    }

    @GetMapping("/people/getAll/admin")
    @JsonView(ResponseView.AdminView.class)
    public People[] getAllAdmin() {
        return PeopleModel.getInstance().getAll().toArray(new People[0]);
    }

    @GetMapping("/people/getCurrentPerson")
    @JsonView(ResponseView.OwnerView.class)
    public People getCurrentPerson() {
        return DormRoomsUserDetailsService.getCurrentUser();
    }
}
