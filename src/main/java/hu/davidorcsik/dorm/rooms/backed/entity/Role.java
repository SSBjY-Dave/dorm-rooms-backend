package hu.davidorcsik.dorm.rooms.backed.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Role implements GrantedAuthority {

    private enum Type {
        RESIDENT, ADMIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ReadOnlyProperty
    private long id;

    @Enumerated(value = EnumType.ORDINAL)
    @ReadOnlyProperty
    private Type role;

    @OneToMany(mappedBy = "role")
    @ToString.Exclude
    @JsonIgnore
    private List<RoleConnector> roleConnectors;

    public long getId() {
        return id;
    }

    public Type getRole() {
        return role;
    }

    @Override
    public String getAuthority() {
        return role.name();
    }
}
