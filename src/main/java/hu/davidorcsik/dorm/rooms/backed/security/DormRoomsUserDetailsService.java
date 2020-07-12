package hu.davidorcsik.dorm.rooms.backed.security;

import hu.davidorcsik.dorm.rooms.backed.entity.People;
import hu.davidorcsik.dorm.rooms.backed.model.PeopleModel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DormRoomsUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<People> p = PeopleModel.getInstance().getDatabaseEntityByNeptunId(username);
        if (p.isEmpty()) throw new UsernameNotFoundException("User " + username + " not found");
        return p.get();
    }
}
