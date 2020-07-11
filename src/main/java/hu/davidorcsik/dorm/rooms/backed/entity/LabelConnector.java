package hu.davidorcsik.dorm.rooms.backed.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
    private long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "people_id", referencedColumnName = "id")
    @ToString.Exclude
    @ReadOnlyProperty
    private People people;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "label_id", referencedColumnName = "id")
    @ToString.Exclude
    @ReadOnlyProperty
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
}
