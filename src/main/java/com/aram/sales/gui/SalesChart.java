package com.aram.sales.gui;


import com.aram.sales.common.RandomGenerator;
import com.aram.sales.model.SalesData;
import com.aram.sales.model.SalesReport;
import com.aram.sales.service.SalesReportService;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.awt.Color.BLACK;
import static java.awt.Font.PLAIN;
import static java.lang.String.format;
import static java.time.LocalDateTime.now;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class SalesChart extends JComponent {

    private static final Font CHART_FONT = new Font("Arial", PLAIN, 24);
    private static final Font TITLE_FONT = new Font("Arial", PLAIN, 36);
    private static final Font TOTAL_SALES_FONT = new Font("Arial", PLAIN, 28);
    private static final Font TIME_OF_LAST_UPDATE_FONT = new Font("Arial", PLAIN, 22);
    private static final Font REFRESH_RATE_FONT = new Font("Arial", PLAIN, 21);;
    private static final String TITLE_TEXT = "Apple Inc. Live Sales";
    private static final String TOTAL_SALES_TEXT = "Total sales since last update = $%.2f";
    private static final String LAST_UPDATED_TEXT = "Last updated: %s";
    private static final String REFRESH_RATE_TEXT = "Refresh rate: %d seconds";

    private final ChartPortionFormatter formatter;
    private final RandomGenerator random;
    private final SalesReportService salesReportService;
    private List<ChartPortion> portions;
    private SalesReport currentSalesReport;
    private final ColorPicker colorPicker;
    private final int refreshRateInSeconds;

    public SalesChart(SalesReportService salesReportService, int refreshRateInSeconds) {
        this.formatter = new ChartPortionFormatter();
        this.random = new RandomGenerator();
        this.colorPicker = new ColorPicker();
        this.refreshRateInSeconds = refreshRateInSeconds;
        this.salesReportService = salesReportService;
        this.currentSalesReport = salesReportService.getSalesReport();
        this.portions = portionsFor(currentSalesReport);
    }

    @Override
    public void paint(Graphics graphics) {
        drawTitle(graphics);
        drawTotal(graphics);
        drawTimeOfLastUpdate(graphics);
        drawRefreshRate(graphics);
        drawChart((Graphics2D) graphics);
    }

    public void queryNewData() {
        currentSalesReport = salesReportService.getSalesReport();
        portions = portionsFor(currentSalesReport);
    }

    private void drawTotal(Graphics graphics) {
        graphics.setFont(TOTAL_SALES_FONT);
        graphics.drawString(format(TOTAL_SALES_TEXT, getTotalSales()), 550, 125);
    }

    private void drawTimeOfLastUpdate(Graphics graphics) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String formattedDateTime = formatter.format(now());
        graphics.setFont(TIME_OF_LAST_UPDATE_FONT);
        graphics.drawString(format(LAST_UPDATED_TEXT, formattedDateTime), 550, 175);
    }

    private void drawTitle(Graphics graphics) {
        graphics.setFont(TITLE_FONT);
        graphics.drawString(TITLE_TEXT, 200, 65);
    }

    private void drawChart(Graphics2D graphics) {
        graphics.setFont(CHART_FONT);
        double degrees = 0;
        int yAxisOfNextLine = 290;
        for (ChartPortion portion : portions) {
            drawPortion(graphics, degrees, portion);
            showInformationForPortion(graphics, portion, yAxisOfNextLine);
            degrees += portion.getValue();
            yAxisOfNextLine += 50;
        }
    }

    private void drawRefreshRate(Graphics graphics) {
        graphics.setFont(REFRESH_RATE_FONT);
        graphics.drawString(format(REFRESH_RATE_TEXT, refreshRateInSeconds), 650, 550);
    }

    private void showInformationForPortion(Graphics2D graphics, ChartPortion portion, int lineHeight) {
        final int squareX = getWidth() - 500;
        graphics.setColor(portion.getColor());
        graphics.fillRect(squareX, lineHeight - 20, 30, 30);
        graphics.setColor(BLACK);
        String portionData = formatPortion(portion);
        graphics.drawString(portionData, squareX + 60, lineHeight);
    }

    private List<ChartPortion> portionsFor(SalesReport salesReport) {
        return salesReport.sales()
                .sorted(comparing(SalesData::getDepartmentName))
                .map(this::mapToChartPortion)
                .collect(toList());
    }

    private ChartPortion mapToChartPortion(SalesData salesData) {
        return new ChartPortion(salesData.getSales(), colorFor(salesData), salesData.getDepartmentName());
    }

    private void drawPortion(Graphics2D graphics, double degrees, ChartPortion portion) {
        graphics.setColor(portion.getColor());
        graphics.fillArc(
                xCoordinateForChart(),
                yCoordinateForChart(),
                chartExtension(),
                chartExtension(),
                startAngleFor(degrees),
                degreesFor(portion)
        );
    }

    private int yCoordinateForChart() {
        return getY() + 150;
    }

    private int xCoordinateForChart() {
        return getX() + 110;
    }

    private int chartExtension() {
        return ((getHeight() / 3) * 2) - 30;
    }

    private int degreesFor(ChartPortion portion) {
        return (int) (portion.getValue() * 360 / getTotal());
    }

    private int startAngleFor(double value) {
        return (int) (value * 360 / getTotal());
    }

    private Color colorFor(SalesData salesData) {
        return colorPicker.colorFor(salesData);
    }

    private double getTotalSales() {
        return currentSalesReport.getTotalSales();
    }

    private double percentageOf(ChartPortion portion) {
        return currentSalesReport.percentageOf(portion.getValue());
    }

    private String formatPortion(ChartPortion portion) {
        return formatter.format(portion, percentageOf(portion));
    }

    private double getTotal() {
        return currentSalesReport.getTotalSales();
    }

}