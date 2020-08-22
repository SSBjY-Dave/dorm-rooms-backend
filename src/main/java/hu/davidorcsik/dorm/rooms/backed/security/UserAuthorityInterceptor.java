package hu.davidorcsik.dorm.rooms.backed.security;

import hu.davidorcsik.dorm.rooms.backed.entity.Role;
import hu.davidorcsik.dorm.rooms.backed.entity.RoleConnector;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserAuthorityInterceptor implements HandlerInterceptor {
    private Set<UserDetails> updateQueue = new HashSet<>();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserWrapper user = (UserWrapper) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!updateQueue.contains(user)) return true;

        List<Role> correctRoles = user.getPeople().getRoleConnectors().stream().map(RoleConnector::getRole).collect(Collectors.toList());
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), correctRoles));

        return true;
    }
}
