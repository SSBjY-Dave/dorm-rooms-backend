package hu.davidorcsik.dorm.rooms.backed.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LabelConnector {
    private long people_id;
    private long label_id;

    public long getPeople_id() {
        return people_id;
    }

    public long getLabel_id() {
        return label_id;
    }
}
