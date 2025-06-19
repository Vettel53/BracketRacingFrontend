package com.example.application.services;

import com.example.application.RunRepo;
import com.example.application.UserRepo;
import com.example.application.models.AppUser;
import com.example.application.models.Run;
import com.example.application.security.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class StatisticsService {

    private final UserRepo userRepo;
    private final RunRepo runRepo;
    private final SecurityService securityService;

    public StatisticsService(UserRepo userRepo, RunRepo runRepo, SecurityService securityService) {
        this.userRepo = userRepo;
        this.runRepo = runRepo;
        this.securityService = securityService;
    }

    public Double getBreakoutPercentage() {
        AppUser appUser = getCurrentUser();
        if (appUser == null) {
            return null;
        }

        int breakoutCounter = 0;
        int totalRuns = 0;

        // TODO: May have to error handle for no runs!
        for (Run run : runRepo.findByAppUser(appUser)) {
            BigDecimal dial = run.getDial();
            BigDecimal fullTrack = run.getFullTrack();

            if (dial.compareTo(fullTrack) > 0 ) {
                breakoutCounter++;
            }
            totalRuns++;
        }

        if (totalRuns == 0) {
            return null;
        }

        // TODO: Make sure "totalRuns" can't be zero before doing this division. Done?
        return ((double) breakoutCounter / totalRuns) * 100;
    }

    private AppUser getCurrentUser() {
        UserDetails loadUserDetails = securityService.getAuthenticatedUser();
        if (loadUserDetails == null) {
            return null;
        }
        String username = loadUserDetails.getUsername();

        return userRepo.findByUsername(username);
    }

    public Double getOverPercentage() {
        AppUser appUser = getCurrentUser();
        if (appUser == null) {
            return null;
        }

        int overCounter = 0;
        int totalRuns = 0;

        for (Run run : runRepo.findByAppUser(appUser)) {
            BigDecimal dial = run.getDial();
            BigDecimal fullTrack = run.getFullTrack();

            if (dial.compareTo(fullTrack) <= 0 ) {
                overCounter++;
            }
            totalRuns++;
        }

        if (totalRuns == 0) {
            return null;
        }

        // TODO : Make sure "totalRuns" can't be zero before doing this division. Done?
        return ((double) overCounter / totalRuns) * 100;
    }

    public BigDecimal getReactionAverage() {
        AppUser appUser = getCurrentUser();
        if (appUser == null) {
            return null;
        }

        int totalRuns = 0;
        BigDecimal totalReactionTime = BigDecimal.ZERO;

        for (Run run : runRepo.findByAppUser(appUser)) {
            BigDecimal reaction = run.getReaction();
            totalReactionTime = totalReactionTime.add(reaction);
            totalRuns++;
        }

        if (totalRuns == 0) {
            return null;
        }

        return totalReactionTime.divide(BigDecimal.valueOf(totalRuns), 4, RoundingMode.HALF_UP);
    }
}
