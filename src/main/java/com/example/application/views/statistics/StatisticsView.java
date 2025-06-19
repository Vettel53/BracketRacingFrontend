package com.example.application.views.statistics;

import com.example.application.services.StatisticsService;
import com.example.application.views.MainView;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.charts.model.style.SolidColor;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import jakarta.annotation.security.PermitAll;


@Route(value = "statistics", layout = MainView.class)
@PermitAll
@UIScope
public class StatisticsView extends VerticalLayout {

    private final StatisticsService statisticsService;
    public StatisticsView(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;

        add(createBreakOutChart());
        add(createReactionTimeChart());
    }

    private Chart createBreakOutChart() {
        // Create Chart
        Chart chart = new Chart(ChartType.PIE);
        chart.getStyle().setBackgroundColor("#f5f5f5");
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Percentage of Break-outs");

        // Configuring Tooltip (Decimals for better precision)
        Tooltip tooltip = new Tooltip();
        tooltip.setValueDecimals(1);
        conf.setTooltip(tooltip);

        // Setting up Pie Chart options
        PlotOptionsPie plotOptions = new PlotOptionsPie();
        plotOptions.setAllowPointSelect(true);
        plotOptions.setCursor(Cursor.POINTER);
        plotOptions.setShowInLegend(true);
        conf.setPlotOptions(plotOptions);

        // Retrieving Break-Out/Ran-Above Stats
        DataSeries series = new DataSeries();
        Double breakOutPercentage = statisticsService.getBreakoutPercentage();
        Double ranAbovePercentage = statisticsService.getOverPercentage();

        // Add correct Data (Edge-case handling)
        if (breakOutPercentage == null || ranAbovePercentage == null) {
            series.add(new DataSeriesItem("No Runs...", 100.00));
        } else {
            series.add(new DataSeriesItem("Break Out", breakOutPercentage));
            series.add(new DataSeriesItem("Ran Above", ranAbovePercentage));
        }

        // Set final settings
        conf.setSeries(series);
        chart.setVisibilityTogglingDisabled(true);

        return chart;
    }


    private Span createReactionTimeChart() {
//        Chart chart = new Chart(ChartType.PIE);
//        Configuration conf = chart.getConfiguration();
//        conf.setTitle("Percentage of Break-outs");
        //Card card = new Card();
        Span reactionTimeLabel;
        if (statisticsService.getReactionAverage() == null) {
            reactionTimeLabel = new Span("Error: Try adding more runs!");
        } else {
            reactionTimeLabel = new Span("Average Reaction Time: " + statisticsService.getReactionAverage() + " sec");
        }

        return reactionTimeLabel;
    }

}
