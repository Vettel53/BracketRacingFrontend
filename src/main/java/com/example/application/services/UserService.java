package com.example.application.services;

import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDetailsManager userDetailsManager;

    public UserService(UserDetailsManager userDetailsManager) {
        this.userDetailsManager = userDetailsManager;
    }

    public boolean userExists(String accountName) {
        return userDetailsManager.userExists(accountName); // Return true or false based on if user exists
    }
}
