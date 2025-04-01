package com.example.application.views.dashboard.dialogs;

import com.example.application.models.Run;
import com.example.application.services.DashboardService;
import com.example.application.views.dashboard.builder.ComponentBuilder;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class EditDialog {

    // "Edit Run" member variables
    private DatePicker editDatePicker;
    private TimePicker editTimePicker;
    private TextField editCar;
    private TextField editDriver;
    private ComboBox<String> editTrack;
    private Select<String> editLane;
    private BigDecimalField editDial;
    private BigDecimalField editReaction;
    private BigDecimalField editSixtyFoot;
    private BigDecimalField editHalfTrack;
    private BigDecimalField editFullTrack;
    private BigDecimalField editSpeed;

    private final DashboardService dashboardService;
    private final AddDialog addDialog;

    public EditDialog(DashboardService dashboardService, AddDialog addDialog) {
        this.dashboardService = dashboardService;
        this.addDialog = addDialog;
    }

    public void loadEditRunDialog(Run runToEdit) {
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
            dashboardService.callRefreshGrid();

            Notification.show("Successfully edited run!", 3000, Notification.Position.TOP_CENTER);
            clearEditTextFields();

            dialog.close();
            // TODO: Maybe somehow which run was edited and undo button?
        });

        cancelEditButton.addClickListener(event -> {
            Notification.show("Cancelled editing run...", 3000, Notification.Position.TOP_CENTER);
            clearEditTextFields();

            dialog.close();
        });
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
        // Do not confuse this with an "add dialog", simply uses helper method to construct the huge amount of tracks
        editTrack = addDialog.createTrackSelectionBox();
        editTrack.setValue(runToEdit.getTrack());
        editLane = new Select<>();
        editLane.setLabel("Select Lane");
        editLane.setItems("Left", "Right");
        editLane.setValue(runToEdit.getLane());
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
        formLayout.add(editDatePicker, editTimePicker, editCar, editDriver, editTrack, editLane, editTrack, editDial, editReaction, editSixtyFoot, editHalfTrack, editFullTrack, editSpeed);
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
}
