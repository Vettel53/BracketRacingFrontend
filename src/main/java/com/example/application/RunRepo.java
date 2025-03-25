package com.example.application;

import com.example.application.models.AppUser;
import com.example.application.models.Run;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RunRepo extends JpaRepository<Run, Long> {
    List<Run> findByAppUser(AppUser appUser);
}
