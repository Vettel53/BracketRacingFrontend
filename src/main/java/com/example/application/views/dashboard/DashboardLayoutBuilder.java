package com.example.application.views.dashboard;

import com.example.application.models.Run;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DashboardLayoutBuilder {
    // TODO: Probably changed methods to default visibility
    private DashboardViewListener listener;

    // Inject using setter injection
    public void setListener(DashboardViewListener listener) {
        this.listener = listener;
    }

    public HorizontalLayout buildMainHorizontalLayout() {
        // Create a new horizontal layout to hold the add run button
        HorizontalLayout hl = new HorizontalLayout();

        // Create add run button
        Button addRunButton = new Button("Add");
        addRunButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addRunButton.addClickShortcut(Key.ENTER);

        // Add event listener to add click event
        addRunButton.addClickListener(event -> {
            // Load the entry run dialog when addRunButton is clicked
            //dashboardView.loadEntryRunDialog();
            listener.onAddRunButtonClick();
        });

        // Add run button to horizontalLayout
        hl.add(addRunButton);
        hl.addClassNames(LumoUtility.Padding.MEDIUM);

        return hl;
    }

    public Grid<Run> buildGridLayout(Grid<Run> grid, ListDataProvider<Run> dataProvider) {
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
                //dashboardView.loadEditRunDialog(runToEdit);
                listener.onEditButtonClick(runToEdit);
                Notification.show("Edit");
            });

            // Instantiate and return a Button for delete action (adding a delete button for each row in the grid)
            Button deleteButton = new Button(VaadinIcon.TRASH.create());
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
            // Click listener
            deleteButton.addClickListener(event -> {
                Run runToDelete = run;
                // Open delete dialog and ask user to delete or not delete
                //dashboardView.loadConfirmDeleteDialog(runToDelete);
                listener.onDeleteButtonClick(runToDelete);
                Notification.show("Delete");
            });

            // Return the delete and edit buttons as a HBox layout
            return new HorizontalLayout(editButton, deleteButton);
        }).setHeader("Manage").setAutoWidth(true).setFrozenToEnd(true);

        return grid;
    }

    //public Dialog c



}
