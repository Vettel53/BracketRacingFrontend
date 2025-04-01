package com.example.application.views.dashboard.components;

import com.example.application.models.Run;
import com.example.application.views.dashboard.builder.ComponentBuilder;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class RunsGrid {

    private final ComponentBuilder componentBuilder;

    public RunsGrid(ComponentBuilder componentBuilder) {
        this.componentBuilder = componentBuilder;
    }

    public Grid<Run> getGrid(Grid<Run> grid, ListDataProvider<Run> dataProvider) {
        grid = componentBuilder.buildGridLayout(grid, dataProvider);

        return grid;
    }
}
