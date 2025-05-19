package com.example.application.views.statistics;

import com.example.application.views.MainView;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.core.userdetails.User;

@Route(value = "statistics", layout = MainView.class)
@PermitAll
@UIScope
public class StatisticsView extends VerticalLayout {

    private final StatisticsService statisticsService;
    public StatisticsView(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;

        Chart chart = new Chart(ChartType.PIE);

        Configuration conf = chart.getConfiguration();

        conf.setTitle("Percentage of Break-outs");

        Tooltip tooltip = new Tooltip();
        tooltip.setValueDecimals(1);
        conf.setTooltip(tooltip);

        PlotOptionsPie plotOptions = new PlotOptionsPie();
        plotOptions.setAllowPointSelect(true);
        plotOptions.setCursor(Cursor.POINTER);
        plotOptions.setShowInLegend(true);
        conf.setPlotOptions(plotOptions);

        DataSeries series = new DataSeries();
        Double breakOutPercentage = statisticsService.getBreakoutPercentage();
        Double ranAbovePercentage  = statisticsService.getOverPercentage();

        if (breakOutPercentage == null || ranAbovePercentage == null) {
            series.add(new DataSeriesItem("No Runs (Enter some on the Dashboard!)", 100.00));
        } else {
            series.add(new DataSeriesItem("Break Out", breakOutPercentage));
            series.add(new DataSeriesItem("Ran Above", ranAbovePercentage));
        }
        conf.setSeries(series);
        chart.setVisibilityTogglingDisabled(true);

        add(chart);
    }

}
