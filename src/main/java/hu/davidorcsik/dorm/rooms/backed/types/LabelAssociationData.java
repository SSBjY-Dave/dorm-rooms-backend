package hu.davidorcsik.dorm.rooms.backed.types;

import hu.davidorcsik.dorm.rooms.backed.entity.Label;
import hu.davidorcsik.dorm.rooms.backed.entity.People;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class LabelAssociationData {
    private People people;
    private Label label;

    public People getPeople() {
        return people;
    }

    public Label getLabel() {
        return label;
    }
}
