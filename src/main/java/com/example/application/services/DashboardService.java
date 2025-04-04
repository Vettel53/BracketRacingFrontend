package com.example.application.services;

import com.example.application.RunRepo;
import com.example.application.UserRepo;
import com.example.application.models.AppUser;
import com.example.application.models.Run;
import com.example.application.security.SecurityService;
import com.example.application.views.dashboard.DashboardView;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@VaadinSessionScope
public class DashboardService {

    // Dependency Injection (constructor)
    private final RunRepo runRepo;
    private final UserRepo userRepo;
    private final SecurityService securityService;

    // Setter injection to access a method in dashboardView
    private DashboardView dashboardView;

    public DashboardService(RunRepo runRepo, UserRepo userRepo, SecurityService securityService) {
        this.runRepo = runRepo;
        this.userRepo = userRepo;
        this.securityService = securityService;
    }

    public String getAuthenticatedUserName() {
        UserDetails loadUserDetails = securityService.getAuthenticatedUser();
        if (loadUserDetails == null) {
            return null;
        }
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
    public void constructRunEntry(Run runToSave) {
        runRepo.save(runToSave);
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
        // Truncate values before setting to database (10.1234 -> 10.123)
        runToEdit.setDial(truncateToValidDecimal(editedDial));
        runToEdit.setReaction(truncateToValidDecimal(editedReaction));
        runToEdit.setSixtyFoot(truncateToValidDecimal(editedSixtyFoot));
        runToEdit.setHalfTrack(truncateToValidDecimal(editedHalfTrack));
        runToEdit.setFullTrack(truncateToValidDecimal(editedFullTrack));
        runToEdit.setSpeed(truncateToValidDecimal(editedSpeed));

        // Save the edited run to the database
        runRepo.save(runToEdit);
    }

    public void deleteRun(Run runToDelete) {
        // Delete the run from the database
        runRepo.delete(runToDelete);
    }

    public void setDashboardView(DashboardView dashboardView) {
        this.dashboardView = dashboardView;
    }

    // TODO: DOC THESE METHODS HEAVY
    public void callUpdateGrid(Run createdRun){
        dashboardView.updateGrid(createdRun);
    }

    public void callRefreshGrid() {
        dashboardView.refreshGrid();
    }

    public void callDeleteAndUpdateGrid(Run runToDelete) {
        dashboardView.deleteAndUpdateGridEntry(runToDelete);
    }

    public BigDecimal truncateToValidDecimal(BigDecimal decimalToTruncate) {
        // Truncate the decimal to 3 decimal places
        return decimalToTruncate.setScale(3, RoundingMode.DOWN);
    }

}
