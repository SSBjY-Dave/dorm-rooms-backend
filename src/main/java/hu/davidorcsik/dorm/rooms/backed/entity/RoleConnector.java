package hu.davidorcsik.dorm.rooms.backed.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.data.annotation.ReadOnlyProperty;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role_connector")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class RoleConnector {
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
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    @ToString.Exclude
    @ReadOnlyProperty
    private Role role;

    public RoleConnector(People people, Role role) {
        this.people = people;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public People getPeople() {
        return people;
    }

    public Role getRole() {
        return role;
    }
}
