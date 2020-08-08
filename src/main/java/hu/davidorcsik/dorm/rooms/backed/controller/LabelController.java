package hu.davidorcsik.dorm.rooms.backed.controller;

import hu.davidorcsik.dorm.rooms.backed.entity.Label;
import hu.davidorcsik.dorm.rooms.backed.model.LabelModel;
import hu.davidorcsik.dorm.rooms.backed.status.LabelRequestStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = { "http://localhost:4200", "http://89.134.96.61:4200"}) //TODO: Modify for production server
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
        List<Label> labels = LabelModel.getInstance().getAll();
        labels.forEach(Label::prepareSerialization);
        return labels.toArray(new Label[0]);
    }
}
