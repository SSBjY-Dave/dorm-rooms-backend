package hu.davidorcsik.dorm.rooms.backed.controller;

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
