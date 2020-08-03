package hu.davidorcsik.dorm.rooms.backed.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hu.davidorcsik.dorm.rooms.backed.entity.People;
import hu.davidorcsik.dorm.rooms.backed.entity.Role;
import hu.davidorcsik.dorm.rooms.backed.model.PeopleModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserWrapper implements UserDetails {
    @JsonIgnore
    private final People people;

    public UserWrapper(People people) {
        this.people = people;
    }

    public People getPeople() {
        return PeopleModel.getInstance().getDatabaseEntity(people).orElse(null);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<Role> grantedRoles = new ArrayList<>();
        people.getRoleConnectors().forEach(rc -> grantedRoles.add(rc.getRole()));
        return grantedRoles;
    }

    @Override
    public String getPassword() {
        return people.getToken();
    }

    @Override
    public String getUsername() {
        return people.getNeptunId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
