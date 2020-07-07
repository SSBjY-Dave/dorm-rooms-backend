package hu.davidorcsik.dorm.rooms.backed.entity;

import hu.davidorcsik.dorm.rooms.backed.status.LabelRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;

@Entity
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Label {
    public static boolean isIdValid(long id) {
        return id > 0;
    }

    public static boolean isNameValid(String name) {
        return !name.isBlank();
    }

    public static ArrayList<LabelRequestStatus> isLabelValid(Label l) {
        ArrayList<LabelRequestStatus> status = new ArrayList<>();

        if (!isIdValid(l.getId())) status.add(LabelRequestStatus.ID_INVALID);
        if (!isNameValid(l.getName())) status.add(LabelRequestStatus.NAME_INVALID);

        return status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (isNameValid(name))
            this.name = name;
        else
            throw new IllegalArgumentException("Inavlid label name");
    }
}
