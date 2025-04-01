package com.example.application.views.dashboard;

import com.example.application.models.AppUser;
import com.example.application.models.Run;
import com.example.application.services.DashboardService;
import com.example.application.views.MainView;
import com.example.application.views.dashboard.builder.ComponentBuilder;
import com.example.application.views.dashboard.components.RunsGrid;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import jakarta.annotation.security.PermitAll;

import java.util.List;


@Route(value = "dashboard", layout = MainView.class)
@PermitAll
@UIScope
public class DashboardView extends VerticalLayout {

    private Grid<Run> grid = new Grid<>(Run.class);
    private final DashboardService dashboardService;
    ListDataProvider<Run> dataProvider;

    private AppUser loggedInAppUser;
    private final ComponentBuilder componentBuilder;
    private final RunsGrid runsGrid;

    public DashboardView(DashboardService dashboardService, ComponentBuilder componentBuilder, RunsGrid runsGrid) {
        this.componentBuilder = componentBuilder;
        this.dashboardService = dashboardService;
        this.runsGrid = runsGrid;

        // Setter injection for service to access this classes methods
        dashboardService.setDashboardView(this);

        loadMainComponents();
    }

    // Loads grid and add run button
    private void loadMainComponents() {
        // Get data for grid population
        dataProvider = getDataProvider();
        grid = runsGrid.getGrid(grid, dataProvider);

        // Add main components to grid
        add(componentBuilder.buildMainHorizontalLayout(loggedInAppUser));
        add(grid);
    }

    // Might put this in another class like service
    private ListDataProvider<Run> getDataProvider() {
        // TODO: Some form of error efficent handling (Make experience smooth for user)
        // Get the username from the authenticated user
        String username = dashboardService.getAuthenticatedUserName();
        if (username == null) {
            return null;
        }
        loggedInAppUser = dashboardService.getAppUserByUsername(username);
        System.out.println("The Logged in User has this username: " + loggedInAppUser.getUsername());

        // Get all runs for the logged-in user
        List<Run> appUserRuns = dashboardService.getAllRunsFromUser(loggedInAppUser);
        // Create dataprovider which holds logged-in user runs
        return dataProvider = new ListDataProvider<>(appUserRuns);
    }

    // TODO: Document these methods heavy
    // TODO: Understand the reason we are using this.getUI()
    // TODO: Understand @UIScope @VaadinSessionScope annotations etc
    public void updateGrid(Run createdRun) {
        this.getUI().get().access(() -> {
            dataProvider.getItems().add(createdRun);
            // Refresh the grid to reflect the changes
            dataProvider.refreshAll();
        });
    }

    public void refreshGrid() {
        this.getUI().get().access(() -> {
            dataProvider.refreshAll();
            grid.recalculateColumnWidths();
        });
    }

    public void deleteAndUpdateGridEntry(Run runToDelete) {
        this.getUI().get().access(() -> {
            dataProvider.getItems().remove(runToDelete);
            dataProvider.refreshAll();
        });
    }
}
