package hu.davidorcsik.dorm.rooms.backed.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.davidorcsik.dorm.rooms.backed.entity.People;
import hu.davidorcsik.dorm.rooms.backed.status.PeopleDataStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @RequestMapping("/")
    public String home() {
        return "home page";
    }
//    @GetMapping("/test/")
//    public String test() throws JsonProcessingException {
//        People p = new People(1, "Orcsik Dávid", "us4gmr", "davidorcsik@protonmail.com", false, "sdfnbsdhjfbsd", (short)1);
//        return new ObjectMapper().writeValueAsString(p);
//    }
}
