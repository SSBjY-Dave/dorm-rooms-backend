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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

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
                .antMatchers("/labelAssociation/disassociate").hasAuthority("ADMIN")
                .antMatchers("/labelAssociation/associate").hasAuthority("ADMIN")
                .antMatchers("/label/add").hasAuthority("ADMIN")
                .antMatchers("/label/delete").hasAuthority("ADMIN")
                .antMatchers("/label/modify").hasAuthority("ADMIN")
                .antMatchers("/label/getAll").hasAuthority("ADMIN")
                .antMatchers("/people/add").hasAuthority("ADMIN")
                .antMatchers("/people/delete").hasAuthority("ADMIN")
                .antMatchers("/people/modify").hasAuthority("ADMIN")
                .antMatchers("/people/getAll/admin").hasAuthority("ADMIN")
                .antMatchers("/reservation/assignToRoom").hasAuthority("ADMIN")
                .antMatchers("/reservation/clearRoom").hasAuthority("ADMIN")
                .antMatchers("/room/setLockState").hasAuthority("ADMIN")
                .antMatchers("/utility/export").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .httpBasic();
//                .and()
//                .headers()
//                .frameOptions().disable();
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
