package hu.davidorcsik.dorm.rooms.backed.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import hu.davidorcsik.dorm.rooms.backed.security.ResponseView;
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
public class Role implements GrantedAuthority {
    public enum Type {
        RESIDENT, ADMIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ReadOnlyProperty
    @JsonView(ResponseView.AdminView.class)
    private long id;

    @Enumerated(value = EnumType.ORDINAL)
    @ReadOnlyProperty
    @JsonView(ResponseView.OwnerView.class)
    private Type role;

    @OneToMany(mappedBy = "role", cascade = CascadeType.DETACH)
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

    public void prepareSerialization() {
        roleConnectors.forEach(RoleConnector::prepareSerializationFromRole);
    }

    void prepareSerializationFromRoleConnector() {
        roleConnectors = null;
    }
}
