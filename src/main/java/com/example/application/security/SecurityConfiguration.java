package com.example.application.security;

import com.example.application.UserRepo;
import com.example.application.models.AppUser;
import com.example.application.views.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends VaadinWebSecurity {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        setLoginView(http, LoginView.class);
    }

    @Bean
    UserDetailsManager userDetailsManager(UserRepo userRepo) {
        AppUser fakeUser = new AppUser("javi");
        AppUser fakeUser2 = new AppUser("kike");

        userRepo.save(fakeUser);
        userRepo.save(fakeUser2);

        return new InMemoryUserDetailsManager(
                User.withUsername("javi")
                        .password("{noop}javi")
                        .roles("USER").build(),
                User.withUsername("kike")
                        .password("{noop}kike")
                        .roles("USER").build()
        );
    }

}
