package com.example.application;

import com.example.application.models.Run;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RunRepo extends JpaRepository<Run, Long> {


}
