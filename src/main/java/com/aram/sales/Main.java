package com.aram.sales;

import com.aram.sales.gui.SalesChart;
import com.aram.sales.service.InMemorySalesReportService;
import com.aram.sales.service.SalesReportService;

import javax.swing.*;

import static java.awt.EventQueue.invokeLater;

public class Main {

    private static final int REFRESH_RATE_IN_SECONDS = 5;
    private static final int REFRESH_RATE_IN_MILLISECONDS = REFRESH_RATE_IN_SECONDS * 1000;

    public static void main(String[] args) {
        invokeLater(Main::showSales);
    }

    private static void showSales() {

        SalesReportService salesReportService = new InMemorySalesReportService();

        SalesChart chart = new SalesChart(salesReportService, REFRESH_RATE_IN_SECONDS);

        JFrame frame = new JFrame();

        frame.getContentPane().add(chart);

        configureWindow(frame);

        showWindow(frame);

        timer(chart).start();

    }

    private static Timer timer(SalesChart chart) {
        return new Timer(REFRESH_RATE_IN_MILLISECONDS, event -> {
            chart.queryNewData();
            chart.repaint();
        });
    }

    private static void configureWindow(JFrame frame) {
        frame.setSize(1100, 700);
        frame.setTitle("Apple Inc. Live Sales Report");
        frame.setResizable(false);
    }

    private static void showWindow(JFrame frame) {
        frame.setVisible(true);
    }

}