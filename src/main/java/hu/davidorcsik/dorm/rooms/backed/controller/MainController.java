package hu.davidorcsik.dorm.rooms.backed.controller;

import hu.davidorcsik.dorm.rooms.backed.entity.People;
import hu.davidorcsik.dorm.rooms.backed.model.PeopleModel;
import hu.davidorcsik.dorm.rooms.backed.model.RoomModel;
import hu.davidorcsik.dorm.rooms.backed.types.ReservationData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//TODO: this controller must not be part of the release build
@RestController
public class MainController {
    @RequestMapping("/")
    public String home() {
        return "home page";
    }
//    @GetMapping("/test/")
//    public ReservationData test() {
//        return new ReservationData(PeopleModel.getInstance().getME(), RoomModel.getInstance().getById());
//    }
}
