package hu.davidorcsik.dorm.rooms.backed.types;

import hu.davidorcsik.dorm.rooms.backed.entity.People;
import hu.davidorcsik.dorm.rooms.backed.entity.Role;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class RoleAssociationData {
    private People people;
    private Role.Type roleType;

    public People getPeople() {
        return people;
    }

    public Role.Type getRoleType() { return roleType; }
}
