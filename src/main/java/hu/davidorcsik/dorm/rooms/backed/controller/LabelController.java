package hu.davidorcsik.dorm.rooms.backed.controller;

import hu.davidorcsik.dorm.rooms.backed.entity.Label;
import hu.davidorcsik.dorm.rooms.backed.model.LabelModel;
import hu.davidorcsik.dorm.rooms.backed.status.LabelRequestStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LabelController {
    @PostMapping("/label/add")
    public LabelRequestStatus[] add(@RequestBody Label l) {
        return LabelModel.getInstance().add(l).toArray(new LabelRequestStatus[0]);
    }

    @PostMapping("/label/delete")
    public LabelRequestStatus[] delete(@RequestBody Label l) {
        return LabelModel.getInstance().delete(l).toArray(new LabelRequestStatus[0]);
    }

    @PostMapping("/label/modify")
    public LabelRequestStatus[] modify(@RequestBody Label l) {
        return LabelModel.getInstance().modify(l).toArray(new LabelRequestStatus[0]);
    }

    @GetMapping("/label/getAll")
    public Label[] getAll() {
        return LabelModel.getInstance().getAll().toArray(new Label[0]);
    }

    //TODO: all mapping except in this controller except getAll is for the admin, they must be protected from normal users
}
