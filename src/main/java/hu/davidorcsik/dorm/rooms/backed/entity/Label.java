package hu.davidorcsik.dorm.rooms.backed.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import hu.davidorcsik.dorm.rooms.backed.status.LabelRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "labels")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
    @OneToMany(mappedBy = "label")
    private List<LabelConnector> labelConnectors;

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

    public List<LabelConnector> getLabelConnectors() {
        return labelConnectors;
    }

    public boolean addLabelConnector(LabelConnector lc) {
        return labelConnectors.add(lc);
    }
}
