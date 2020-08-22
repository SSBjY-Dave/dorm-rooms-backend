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
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role_connector")
public class RoleConnector {
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
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    @ToString.Exclude
    @ReadOnlyProperty
    @JsonView(ResponseView.OwnerView.class)
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

    public void prepareSerialization() {
        people.prepareSerializationFromRoleConnector();
        role.prepareSerializationFromRoleConnector();
    }

    void prepareSerializationFromPeople() {
        people = null;
        role.prepareSerializationFromRoleConnector();
    }

    void prepareSerializationFromRole() {
        role = null;
        people.prepareSerializationFromRoleConnector();
    }
}
