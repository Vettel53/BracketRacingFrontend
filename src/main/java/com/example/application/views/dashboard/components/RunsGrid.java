package com.example.application.views.dashboard.components;

import com.example.application.models.Run;
import com.example.application.views.dashboard.builder.DashboardBuilder;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class RunsGrid {

    private final DashboardBuilder dashboardBuilder;

    public RunsGrid(DashboardBuilder dashboardBuilder) {
        this.dashboardBuilder = dashboardBuilder;
    }

    public Grid<Run> getGrid(Grid<Run> grid, ListDataProvider<Run> dataProvider) {
        grid = dashboardBuilder.buildGridLayout(grid, dataProvider);

        return grid;
    }
}
