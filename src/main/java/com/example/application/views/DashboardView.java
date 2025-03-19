package com.example.application.views;

import com.example.application.models.Run;
import com.example.application.RunRepo;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Route(value = "dashboard", layout = MainView.class)
public class DashboardView extends VerticalLayout {

    private final RunRepo repo;
    private final Grid<Run> grid = new Grid<>(Run.class);
    private final HorizontalLayout hl = new HorizontalLayout();
    ListDataProvider<Run> dataProvider;

    // Add run instance variables
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

    // Edit run instance variables
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

    public DashboardView(RunRepo repo) {
        this.repo = repo;

        loadAddRunEntryButton();
        add(grid);
        loadRuns();
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

    private void loadRuns() {
        List<Run> runs = repo.findAll();
        dataProvider = new ListDataProvider<>(runs);
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
        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(saveButton);

        dialog.open();

        // Save button click listener
        saveButton.addClickListener(event -> {
            // Access values from the instance variables
//            LocalDate date = datePicker.getValue();
//            LocalTime time = timePicker.getValue();
//            String carText = car.getValue();
//            String driverText = driver.getValue();
//            String trackText = track.getValue();
//            String laneText = lane.getValue();
//            BigDecimal dialText = dial.getValue();
//            BigDecimal reactionText = reaction.getValue();
//            BigDecimal sixtyFootText = sixtyFoot.getValue();
//            BigDecimal halfTrackText = halfTrack.getValue();
//            BigDecimal fullTrackText = fullTrack.getValue();
//            BigDecimal speedText = speed.getValue();

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
            Run newRun = new Run(date, time, carText, driverText, trackText, laneText, dialText, reactionText, sixtyFootText, halfTrackText, fullTrackText, speedText);
                // Save to H2 database
                repo.save(newRun);

                // We can use fieldName.clear() to clear the fields, but we are testing so disabled for now
                // Get items, add new run, and then refresh the DataProvider for the grid
                dataProvider.getItems().add(newRun);
            // Refresh the grid to reflect the changes
            dataProvider.refreshAll();
            Notification.show("Successfully added new run to database!");
            // TODO: Should probably clear fields here
            dialog.close();
        });

        cancelButton.addClickListener(event -> {
            Notification.show("Cancelled adding run...");
            // TODO: Should probably clear fields here
            dialog.close();
        });
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
        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(saveButton);

        dialog.open();

        saveButton.addClickListener(event -> {
            // Set new values to the run to be edited
            // TODO: Make sure to clear field values later
            runToEdit.setDate(editDatePicker.getValue());
            runToEdit.setTime(editTimePicker.getValue());
            runToEdit.setCar(editCar.getValue());
            runToEdit.setDriver(editDriver.getValue());
            runToEdit.setTrack(editTrack.getValue());
            //runToEdit.setLane(comboBox.getValue());
            runToEdit.setDial(editDial.getValue());
            runToEdit.setReaction(editReaction.getValue());
            runToEdit.setSixtyFoot(editSixtyFoot.getValue());
            runToEdit.setHalfTrack(editHalfTrack.getValue());
            runToEdit.setFullTrack(editFullTrack.getValue());
            runToEdit.setSpeed(editSpeed.getValue());
            // Save the edited run to the database
            repo.save(runToEdit);
            // Refresh the grid to reflect the changes
            dataProvider.refreshAll();
            grid.recalculateColumnWidths();
            dialog.close();

            Notification.show("Successfully edited run!");
            // TODO: Maybe somehow which run was edited and undo button?
        });

        cancelButton.addClickListener(event -> {
            Notification.show("Cancelled editing run...");
            // TODO: Should probably clear field values here later when not using test values
            dialog.close();
        });
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
            repo.delete(runToDelete);
            dataProvider.getItems().remove(runToDelete);
            // Refresh the grid to reflect the changes
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
//        editLane = new Select<>();
//        editLane.setLabel("Select Lane");
//        editLane.setItems("Left", "Right");
//        editLane.setValue(runToEdit.getLane());
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
        formLayout.add(editDatePicker, editTimePicker, editCar, editDriver, editTrack, comboBox, editDial, editReaction, editSixtyFoot, editHalfTrack, editFullTrack, editSpeed);
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
