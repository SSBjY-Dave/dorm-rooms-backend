package hu.davidorcsik.dorm.rooms.backed.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class MVCConfig extends WebMvcConfigurationSupport {
    private final UserAuthorityInterceptor userAuthorityInterceptor;

    @Autowired
    public MVCConfig(UserAuthorityInterceptor userAuthorityInterceptor) {
        this.userAuthorityInterceptor = userAuthorityInterceptor;
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userAuthorityInterceptor);
    }
}
