package com.example.application.services;

import com.example.application.UserRepo;
import com.example.application.models.AppUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class AccountCreationService {

    private final UserRepo userRepo;
    private final UserDetailsManager userDetailsManager;

    public AccountCreationService(UserRepo userRepo, UserDetailsManager userDetailsManager) {
        this.userRepo = userRepo;
        this.userDetailsManager = userDetailsManager;
    }

    public void createUser(String username, String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password); // Hash the password

        userDetailsManager.createUser(
                User.withUsername(username)
                        .password("{noop}" + password) // `{noop}` = no encoding, use only for testing!
                        .roles("USER").build()
        );

        AppUser newUser = new AppUser(username, password); // Store hashed password
        userRepo.save(newUser);

        // TODO: Follow spring security tutorial
    }
}
