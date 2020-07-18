package hu.davidorcsik.dorm.rooms.backed.security;

import hu.davidorcsik.dorm.rooms.backed.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService = new DormRoomsUserDetailsService();
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        return provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/labelAssociation/disassociate").hasRole("ADMIN")
                .antMatchers("/labelAssociation/associate").hasRole("ADMIN")
                .antMatchers("/label/add").hasRole("ADMIN")
                .antMatchers("/label/delete").hasRole("ADMIN")
                .antMatchers("/label/modify").hasRole("ADMIN")
                .antMatchers("/label/getAll").hasRole("ADMIN")
                .antMatchers("/people/add").hasRole("ADMIN")
                .antMatchers("/people/delete").hasRole("ADMIN")
                .antMatchers("/people/modify").hasRole("ADMIN")
                .antMatchers("/reservation/assignToRoom").hasRole("ADMIN")
                .antMatchers("/reservation/clearRoom").hasRole("ADMIN")
                .antMatchers("/room/setLockState").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .httpBasic();
                //.and()
                //.sessionManagement();
                //.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
