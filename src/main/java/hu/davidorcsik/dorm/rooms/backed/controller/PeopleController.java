package hu.davidorcsik.dorm.rooms.backed.controller;

import com.fasterxml.jackson.annotation.JsonView;
import hu.davidorcsik.dorm.rooms.backed.entity.People;
import hu.davidorcsik.dorm.rooms.backed.entity.RoleConnector;
import hu.davidorcsik.dorm.rooms.backed.model.PeopleModel;
import hu.davidorcsik.dorm.rooms.backed.security.DormRoomsUserDetailsService;
import hu.davidorcsik.dorm.rooms.backed.security.ResponseView;
import hu.davidorcsik.dorm.rooms.backed.status.PeopleRequestStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = { "http://localhost:4200", "http://89.134.96.61:4200"}) //TODO: Modify for production server
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
        //TODO: Figure out how to skip room connectors in one way because I can't anymore.......
        List<People> people = PeopleModel.getInstance().getAll();
        people.forEach(People::prepareSerialization);
        return people.toArray(new People[0]);
    }

    @GetMapping("/people/getAll/admin")
    @JsonView(ResponseView.AdminView.class)
    public People[] getAllAdmin(){
        //TODO: Figure out how to skip room connectors in one way because I can't anymore.......
        List<People> people = PeopleModel.getInstance().getAll();
        people.forEach(People::prepareSerialization);
        return people.toArray(new People[0]);
    }

    @GetMapping("/people/getCurrentPerson")
    @JsonView(ResponseView.OwnerView.class)
    public People getCurrentPerson() {
        People currentPerson = DormRoomsUserDetailsService.getCurrentUser();
        currentPerson.prepareSerialization();
        return currentPerson;
    }
}
