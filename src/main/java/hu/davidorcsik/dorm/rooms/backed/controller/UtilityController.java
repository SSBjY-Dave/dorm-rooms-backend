package hu.davidorcsik.dorm.rooms.backed.controller;

import hu.davidorcsik.dorm.rooms.backed.model.UtilityModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:4200") //TODO: Modify for production server
public class UtilityController {

    @GetMapping("/utility/export")
    public String export() {
        return UtilityModel.getInstance().export();
    }
}
