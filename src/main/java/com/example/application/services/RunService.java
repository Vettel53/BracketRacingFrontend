package com.example.application.services;

import com.example.application.RunRepo;
import com.example.application.models.AppUser;
import com.example.application.models.Run;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RunService {

    private RunRepo runRepo;

    public RunService(RunRepo runRepo) {
        this.runRepo = runRepo;
    }

    public List<Run> getUserRuns(AppUser appUser) {
        return runRepo.findByAppUser(appUser);
    }

}
