package hu.davidorcsik.dorm.rooms.backed.security;

import hu.davidorcsik.dorm.rooms.backed.entity.People;
import hu.davidorcsik.dorm.rooms.backed.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserWrapper implements UserDetails {
    private final People people;

    public UserWrapper(People people) {
        this.people = people;
    }

    public People getPeople() {
        return people;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<Role> grantedRoles = new ArrayList<>();
        people.getRoleConnector().forEach(rc -> grantedRoles.add(rc.getRole()));
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
