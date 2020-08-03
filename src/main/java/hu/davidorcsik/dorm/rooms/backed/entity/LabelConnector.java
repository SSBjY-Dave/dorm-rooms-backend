package hu.davidorcsik.dorm.rooms.backed.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import hu.davidorcsik.dorm.rooms.backed.security.ResponseView;
import lombok.*;
import org.springframework.data.annotation.ReadOnlyProperty;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "label_connector")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class LabelConnector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ReadOnlyProperty
    @JsonView(ResponseView.AdminView.class)
    private long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "people_id", referencedColumnName = "id")
    @ToString.Exclude
    @ReadOnlyProperty
    @JsonView(ResponseView.AdminView.class)
    private People people;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "label_id", referencedColumnName = "id")
    @ToString.Exclude
    @ReadOnlyProperty
    @JsonView(ResponseView.AdminView.class)
    private Label label;

    public LabelConnector(People people, Label label) {
        this.people = people;
        this.label = label;
    }

    public long getId() {
        return id;
    }

    public People getPeople() {
        return people;
    }

    public Label getLabel() {
        return label;
    }

    public void prepareSerialization() {
        people.prepareSerializationFromLabelConnector();
        label.prepareSerializationFromLabelConnector();
    }

    void prepareSerializationFromPeople() {
        people = null;
        label.prepareSerializationFromLabelConnector();
    }

    void prepareSerializationFromLabel() {
        label = null;
        people.prepareSerializationFromLabelConnector();
    }
}
