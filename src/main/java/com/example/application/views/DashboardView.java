package com.example.application.views;

import com.example.application.UserRepo;
import com.example.application.models.AppUser;
import com.example.application.models.Run;
import com.example.application.RunRepo;
import com.example.application.security.SecurityService;
import com.example.application.services.DashboardService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Route(value = "dashboard", layout = MainView.class)
@PermitAll
public class DashboardView extends VerticalLayout {

    private final RunRepo runRepo;
    private final Grid<Run> grid = new Grid<>(Run.class);
    private final HorizontalLayout hl = new HorizontalLayout();
    private final DashboardService dashboardService;
    ListDataProvider<Run> dataProvider;

    // "Add run" instance variables
    private DatePicker datePicker;
    private TimePicker timePicker;
    private TextField car;
    private TextField driver;
    private TextField track;
    private Select<String> lane;
    private BigDecimalField dial;
    private BigDecimalField reaction;
    private BigDecimalField sixtyFoot;
    private BigDecimalField halfTrack;
    private BigDecimalField fullTrack;
    private BigDecimalField speed;

    // "Edit Run" instance variables
    private DatePicker editDatePicker;
    private TimePicker editTimePicker;
    private TextField editCar;
    private TextField editDriver;
    private TextField editTrack;
    private Select<String> editLane;
    private BigDecimalField editDial;
    private BigDecimalField editReaction;
    private BigDecimalField editSixtyFoot;
    private BigDecimalField editHalfTrack;
    private BigDecimalField editFullTrack;
    private BigDecimalField editSpeed;

    private AppUser loggedInAppUser;

    public DashboardView(RunRepo runRepo, SecurityService securityService, DashboardService dashboardService, UserRepo userRepo) {
        this.runRepo = runRepo;
        this.dashboardService = dashboardService;
        loadAddRunEntryButton();
        add(grid);
        loadRunsFromAppUser();
    }

    private void loadAddRunEntryButton() {
        // Create add run button
        Button addRunButton = new Button("Add");
        addRunButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addRunButton.addClickShortcut(Key.ENTER);
        // Add event listener to add click event
        addRunButton.addClickListener(event -> {
            // Load the entry run dialog when addRunButton is clicked
            loadRunEntryDialog();
        });

        // Add run button to horizontalLayout
        hl.add(addRunButton);
        hl.addClassNames(LumoUtility.Padding.MEDIUM);
        // Add to extended VerticalLayout in class
        add(hl);
    }

    private void loadRunsFromAppUser() {
        // TODO: Some form of error efficent handling (Make experience smooth for user)
        // Get the username from the authenticated user
        String username = dashboardService.getAuthenticatedUserName();
        loggedInAppUser = dashboardService.getAppUserByUsername(username);
        System.out.println("The Logged in User has this username: " + loggedInAppUser.getUsername());

        // Get all runs for the logged-in user
        List<Run> appUserRuns = dashboardService.getAllRunsFromUser(loggedInAppUser);

        // Create a data provider for the grid and set themes
        dataProvider = new ListDataProvider<>(appUserRuns);
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.setDataProvider(dataProvider);

        // Remove all auto-generated columns by Vaadin
        grid.removeAllColumns();

        // TODO: Missing id, date, and time
        // Add each column individually and what attributes/features they will need
        grid.addColumn(Run::getCar).setHeader("Car").setFrozen(true).setAutoWidth(true).setSortable(true);
        grid.addColumn(Run::getDriver).setHeader("Driver").setAutoWidth(true).setSortable(true);
        grid.addColumn(Run::getTrack).setHeader("Track").setAutoWidth(true).setSortable(true);
        grid.addColumn(Run::getLane).setHeader("Lane").setAutoWidth(true).setSortable(true);
        grid.addColumn(Run::getDial).setHeader("Dial").setAutoWidth(true).setSortable(true);
        grid.addColumn(Run::getReaction).setHeader("Reaction").setAutoWidth(true).setSortable(true);
        grid.addColumn(Run::getSixtyFoot).setHeader("60'").setAutoWidth(true).setSortable(true);
        grid.addColumn(Run::getHalfTrack).setHeader("330'").setAutoWidth(true).setSortable(true);
        grid.addColumn(Run::getFullTrack).setHeader("660'").setAutoWidth(true).setSortable(true);
        grid.addColumn(Run::getSpeed).setHeader("Speed").setAutoWidth(true).setSortable(true);

        // TODO: Understand where it's referncing this run in the lamda expression
        grid.addComponentColumn(run -> {
            // Edit button
            Button editButton = new Button(VaadinIcon.EDIT.create());
            editButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            editButton.addClickListener(event -> {
                Run runToEdit = run;
                // Open the edit dialog with the existing run data
                loadEditRunDialog(runToEdit);
            });

            // Instantiate and return a Button for delete action (adding a delete button for each row in the grid)
            Button deleteButton = new Button(VaadinIcon.TRASH.create());
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
            // Click listener
            deleteButton.addClickListener(event -> {
                Run runToDelete = run;
                // Open delete dialog and ask user to delete or not delete
                loadConfirmDeleteDialog(runToDelete);
            });

            // Return the delete and edit buttons as a HBox layout
            return new HorizontalLayout(editButton, deleteButton);
        }).setHeader("Manage").setAutoWidth(true).setFrozenToEnd(true);
    }

    private void loadRunEntryDialog() {
        // Instantiate Dialog Vaadin Box
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("New Run");

        // Call helper method to generate vertical layout
        VerticalLayout dialogLayout = createRunEntryDialogLayout();
        // Add VerticalLayout to dialog box
        dialog.add(dialogLayout);

        // Add save and cancel buttons to dialog footer
        Button addRunButton = new Button("Save");
        Button cancelRunButton = new Button("Cancel");
        dialog.getFooter().add(cancelRunButton);
        dialog.getFooter().add(addRunButton);

        dialog.open();

        // Save button click listener TODO: Error handling for null fields
        addRunButton.addClickListener(event -> {
            saveRunButtonClick();
            clearAddEntryTextFields();

            dialog.close();
        });

        cancelRunButton.addClickListener(event -> {
            Notification.show("Cancelled adding run...");
            clearAddEntryTextFields();

            dialog.close();
        });

    }

    private void saveRunButtonClick() {
        // Access values from the instance variables
        LocalDate date = datePicker.getValue();
        LocalTime time = timePicker.getValue();
        String carText = car.getValue();
        String driverText = driver.getValue();
        String trackText = track.getValue();
        String laneText = lane.getValue();
        BigDecimal dialText = dial.getValue();
        BigDecimal reactionText = reaction.getValue();
        BigDecimal sixtyFootText = sixtyFoot.getValue();
        BigDecimal halfTrackText = halfTrack.getValue();
        BigDecimal fullTrackText = fullTrack.getValue();
        BigDecimal speedText = speed.getValue();

        // Create a new Run object with the entered values and save it to the database
        // NOTE: This is commented out for testing, use this in production.
        //Run newRun = dashboardService.constructRunEntry(loggedInAppUser, date, time, carText, driverText, trackText, laneText, dialText, reactionText, sixtyFootText, halfTrackText, fullTrackText, speedText);

        // NOTE: Creates fake run entry with autofilled values, DO NOT use in production
        Run newFakeRun = dashboardService.constructFakeRunEntry(loggedInAppUser);

        // Get items, add new run, and then refresh the DataProvider for the grid
        // NOTE: Commented out for testing, use this in production
        // dataProvider.getItems().add(newRun);

        // NOTE: For development mode, use above method for production.
        dataProvider.getItems().add(newFakeRun);
        // Refresh the grid to reflect the changes
        dataProvider.refreshAll();
        Notification.show("Successfully added new run to database!");
    }

    private void clearAddEntryTextFields() {
        // Clear all text fields
        datePicker.clear();
        timePicker.clear();
        car.clear();
        driver.clear();
        track.clear();
        lane.clear();
        dial.clear();
        reaction.clear();
        sixtyFoot.clear();
        halfTrack.clear();
        fullTrack.clear();
        speed.clear();
    }

    private void loadEditRunDialog(Run runToEdit) {
        // Instantiate Dialog Vaadin Box
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Edit Run");

        // Call helper method to generate vertical layout
        VerticalLayout dialogLayout = createEditRunLayout(runToEdit);
        // Add VerticalLayout to dialog box
        dialog.add(dialogLayout);

        // Add save and cancel buttons to dialog footer
        Button saveEditButton = new Button("Save");
        Button cancelEditButton = new Button("Cancel");
        dialog.getFooter().add(cancelEditButton);
        dialog.getFooter().add(saveEditButton);

        dialog.open();

        saveEditButton.addClickListener(event -> {
            // TODO: Error handling for null values, it will produce on error.
            // Save the edited run into database
            dashboardService.saveEditedRun(
                    runToEdit,
                    editDatePicker.getValue(),
                    editTimePicker.getValue(),
                    editCar.getValue(),
                    editDriver.getValue(),
                    editTrack.getValue(),
                    editLane.getValue(),
                    editDial.getValue(),
                    editReaction.getValue(),
                    editSixtyFoot.getValue(),
                    editHalfTrack.getValue(),
                    editFullTrack.getValue(),
                    editSpeed.getValue()
            );

            // Refresh the grid to reflect the changes
            dataProvider.refreshAll();
            grid.recalculateColumnWidths();

            Notification.show("Successfully edited run!");
            clearEditTextFields();

            dialog.close();
            // TODO: Maybe somehow which run was edited and undo button?
        });

        cancelEditButton.addClickListener(event -> {
            Notification.show("Cancelled editing run...");
            clearEditTextFields();

            dialog.close();
        });
    }

    private void clearEditTextFields() {
        // Clear all text fields
        editDatePicker.clear();
        editTimePicker.clear();
        editCar.clear();
        editDriver.clear();
        editTrack.clear();
        editLane.clear();
        editDial.clear();
        editReaction.clear();
        editSixtyFoot.clear();
        editHalfTrack.clear();
        editFullTrack.clear();
        editSpeed.clear();
    }

    private void loadConfirmDeleteDialog(Run runToDelete) {
        // Instantiate Dialog Delete Box
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Are you sure you want to delete this run?");

        // Call helper method to generate vetical layout
        VerticalLayout dialogLayout = createDeleteRunDialogLayout();
        // Add VerticalLayout to dialog box
        dialog.add(dialogLayout);

        // Add yes and no buttons to dialog footer
        Button yesButton = new Button("Delete Run");
        Button noButton = new Button("Cancel");
        dialog.getFooter().add(noButton);
        dialog.getFooter().add(yesButton);

        dialog.open();

        yesButton.addClickListener(event -> {
            // Delete the run from the database
            dashboardService.deleteRun(runToDelete);

            // Refresh the grid/data-provider to reflect the changes
            dataProvider.getItems().remove(runToDelete);
            dataProvider.refreshAll();

            dialog.close();

            Notification.show("Successfully deleted run!");
            // TODO: Maybe show which run is going to be deleted? Expandable box
            // TODO: Undo button?
        });

        noButton.addClickListener(event -> {
            Notification.show("Cancelled deleting run...");
            dialog.close();
        });
    }

    private VerticalLayout createRunEntryDialogLayout() {
        // Initialize instance variables
        datePicker = new DatePicker("Date");
        timePicker = new TimePicker("Time");
        car = new TextField("Car");
        driver = new TextField("Driver");
        track = new TextField("Track");
        lane = new Select<>();
            lane.setLabel("Select Lane");
            lane.setItems("Left", "Right");
        dial = new BigDecimalField("Dial");
        reaction = new BigDecimalField("Reaction");
        sixtyFoot = new BigDecimalField("60' Foot");
        halfTrack = new BigDecimalField("330' Track");
        fullTrack = new BigDecimalField("660' Track");
        speed = new BigDecimalField("Speed MPH");

        // Initialize FormLayout with correct properties
        FormLayout formLayout = new FormLayout();
        formLayout.add(datePicker, timePicker, car, driver, track, lane, dial, reaction, sixtyFoot, halfTrack, fullTrack, speed);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1), new FormLayout.ResponsiveStep("600px", 2));

        // Initialize VerticalLayout with correct properties (FormLayout atm)
        VerticalLayout dialogLayout = new VerticalLayout(formLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "24rem").set("max-width", "100%");

        // Return VerticalLayout
        return dialogLayout;
    }


    private VerticalLayout createEditRunLayout(Run runToEdit) {
        // Initialize instance variables and set their values to the runToEdit values
        editDatePicker = new DatePicker("Date");
        editDatePicker.setValue(runToEdit.getDate());
        editTimePicker = new TimePicker("Time");
        editTimePicker.setValue(runToEdit.getTime());
        editCar = new TextField("Car");
        editCar.setValue(runToEdit.getCar());
        editDriver = new TextField("Driver");
        editDriver.setValue(runToEdit.getDriver());
        editTrack = new TextField("Track");
        editTrack.setValue(runToEdit.getTrack());

        editLane = new Select<>();
        editLane.setLabel("Select Lane");
        editLane.setItems("Left", "Right");
        editLane.setValue(runToEdit.getLane());

        ComboBox<String> comboBox = new ComboBox<>("Tracks");
        comboBox.setItems("EMP", "XRP", "CRP", "TRP", "PV");
        editDial = new BigDecimalField("Dial");
        editDial.setValue(runToEdit.getDial());
        editReaction = new BigDecimalField("Reaction");
        editReaction.setValue(runToEdit.getReaction());
        editSixtyFoot = new BigDecimalField("60' Foot");
        editSixtyFoot.setValue(runToEdit.getSixtyFoot());
        editHalfTrack = new BigDecimalField("330' Track");
        editHalfTrack.setValue(runToEdit.getHalfTrack());
        editFullTrack = new BigDecimalField("660' Track");
        editFullTrack.setValue(runToEdit.getFullTrack());
        editSpeed = new BigDecimalField("Speed MPH");
        editSpeed.setValue(runToEdit.getSpeed());

        // Initialize FormLayout with correct properties
        FormLayout formLayout = new FormLayout();
        formLayout.add(editDatePicker, editTimePicker, editCar, editDriver, editTrack, editLane, comboBox, editDial, editReaction, editSixtyFoot, editHalfTrack, editFullTrack, editSpeed);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1), new FormLayout.ResponsiveStep("600px", 2));

        // Initialize VerticalLayout with correct properties (FormLayout atm)
        VerticalLayout dialogLayout = new VerticalLayout(formLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "24rem").set("max-width", "100%");

        // Return VerticalLayout
        return dialogLayout;
    }

    private VerticalLayout createDeleteRunDialogLayout() {
            // Initialize VerticalLayout with correct properties
            VerticalLayout dialogLayout = new VerticalLayout();
            dialogLayout.setPadding(false);
            dialogLayout.setSpacing(false);
            dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
            dialogLayout.getStyle().set("width", "24rem").set("max-width", "100%");

            // Return VerticalLayout
            return dialogLayout;
    }
}
