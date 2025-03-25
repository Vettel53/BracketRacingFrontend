package com.example.application.models;

import jakarta.persistence.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    // TODO: Understand this relationship deeper
    //@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OneToMany(mappedBy = "appUser")
    private List<Run> runDetails;

    public AppUser() {
    }

    public AppUser(String username) {
        this.username = username;
    }

    public AppUser(String username, List<Run> runDetails) {
        this.username = username;
        this.runDetails = runDetails;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Run> getRunDetails() {
        return runDetails;
    }

    public void setRunDetails(List<Run> runDetails) {
        this.runDetails = runDetails;
    }
}
