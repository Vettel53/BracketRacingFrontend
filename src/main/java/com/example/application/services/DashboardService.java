package com.example.application.services;

import com.example.application.RunRepo;
import com.example.application.UserRepo;
import com.example.application.models.AppUser;
import com.example.application.models.Run;
import com.example.application.security.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class DashboardService {

    // Dependency Injection (constructor)
    private final RunRepo runRepo;
    private final UserRepo userRepo;
    private final SecurityService securityService;

    public DashboardService(RunRepo runRepo, UserRepo userRepo, SecurityService securityService) {
        this.runRepo = runRepo;
        this.userRepo = userRepo;
        this.securityService = securityService;
    }

    public String getAuthenticatedUserName() {
        UserDetails loadUserDetails = securityService.getAuthenticatedUser();
        System.out.println(loadUserDetails);
        String username = loadUserDetails.getUsername();
        System.out.println("The Authenticated User returned this username: " + username);
        return username;
    }

    public AppUser getAppUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public List<Run> getAllRunsFromUser(AppUser loggedInAppUser) {
        return runRepo.findByAppUser(loggedInAppUser);
    }

    // Used to create fake run entries for ease
    public Run constructFakeRunEntry(AppUser loggedInAppUser) {
        // Fake values for LocalDate and LocalTime
        LocalDate date = LocalDate.of(2023, 10, 26); // Example date
        LocalTime time = LocalTime.of(14, 30); // Example time

        // Fake values for String fields
        String carText = "1969 Camaro";
        String driverText = "John Doe";
        String trackText = "Local Dragstrip";
        String laneText = "Left";

        // Fake values for BigDecimal fields
        BigDecimal dialText = new BigDecimal("10.50");
        BigDecimal reactionText = new BigDecimal("0.123");
        BigDecimal sixtyFootText = new BigDecimal("1.85");
        BigDecimal halfTrackText = new BigDecimal("7.503");
        BigDecimal fullTrackText = new BigDecimal("11.90");
        BigDecimal speedText = new BigDecimal("115.75");

        // Create a new Run object with the entered values and save it to the database
        Run newFakeRun = new Run(loggedInAppUser, date, time, carText, driverText, trackText, laneText, dialText, reactionText, sixtyFootText, halfTrackText, fullTrackText, speedText);

        // Save to H2 database
        runRepo.save(newFakeRun);

        return newFakeRun;
    }


    // Used to create a new REAL run entry for the authenticated user
    public Run constructRunEntry(AppUser loggedInAppUser, LocalDate date, LocalTime time, String carText, String driverText, String trackText, String laneText, BigDecimal dialText, BigDecimal reactionText, BigDecimal sixtyFootText, BigDecimal halfTrackText, BigDecimal fullTrackText, BigDecimal speedText) {
        // Create a new Run object with the entered values and save it to the database
        Run newUserCreatedRun = new Run(loggedInAppUser, date, time, carText, driverText, trackText, laneText, dialText, reactionText, sixtyFootText, halfTrackText, fullTrackText, speedText);
        // Save to H2 database
        runRepo.save(newUserCreatedRun);

        return newUserCreatedRun;
    }

    public void saveEditedRun(Run runToEdit, LocalDate editedDate, LocalTime editedTime, String editedCar, String editedDriver, String editedTrack, String editedLane, BigDecimal editedDial, BigDecimal editedReaction, BigDecimal editedSixtyFoot, BigDecimal editedHalfTrack, BigDecimal editedFullTrack, BigDecimal editedSpeed) {
        // Set new values to the run to be edited
        // TODO: Make sure to clear field values later
        runToEdit.setDate(editedDate);
        runToEdit.setTime(editedTime);
        runToEdit.setCar(editedCar);
        runToEdit.setDriver(editedDriver);
        runToEdit.setTrack(editedTrack);
        runToEdit.setLane(editedLane);
        runToEdit.setDial(editedDial);
        runToEdit.setReaction(editedReaction);
        runToEdit.setSixtyFoot(editedSixtyFoot);
        runToEdit.setHalfTrack(editedHalfTrack);
        runToEdit.setFullTrack(editedFullTrack);
        runToEdit.setSpeed(editedSpeed);

        // Save the edited run to the database
        runRepo.save(runToEdit);
    }

    public void deleteRun(Run runToDelete) {
        // Delete the run from the database
        runRepo.delete(runToDelete);
    }


}
