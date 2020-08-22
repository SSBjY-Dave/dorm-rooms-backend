package hu.davidorcsik.dorm.rooms.backed.security;

import hu.davidorcsik.dorm.rooms.backed.entity.People;
import hu.davidorcsik.dorm.rooms.backed.entity.Role;
import hu.davidorcsik.dorm.rooms.backed.model.PeopleModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DormRoomsUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserByUsername(username);
    }
    public static UserDetails getUserByUsername(String username) throws UsernameNotFoundException {
        Optional<People> p = PeopleModel.getInstance().getDatabaseEntityByNeptunId(username);
        if (p.isEmpty()) throw new UsernameNotFoundException("User " + username + " not found");
        return new UserWrapper(p.get());
    }
    public static People getCurrentUser() {
        return ((UserWrapper) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPeople();
    }
    public static boolean isCurrentUserAdmin() {
        return ((UserWrapper) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(Role.Type.ADMIN.name()));
    }
    public static boolean isCurrentUserResident() {
        return ((UserWrapper) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(Role.Type.RESIDENT.name()));
    }
}
