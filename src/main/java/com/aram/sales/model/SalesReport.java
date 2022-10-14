package com.aram.sales.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class SalesReport {

    private final Map<SalesData, Percentage>  report;
    private final double totalSales;

    public SalesReport(Collection<? extends SalesData> sales) {
        this.totalSales = calculateTotalSales(sales);
        this.report = generateReport(sales);
    }

    public Map<SalesData, Percentage> getReport() {
        return report;
    }

    public Stream<SalesData> sales() {
        return report.keySet().stream();
    }

    public double getTotalSales() {
        return totalSales;
    }

    @Override
    public String toString() {
        return "SalesReport{" +
                "totalSales=" + totalSales +
                "report=" + report +
                '}';
    }

    private Map<SalesData, Percentage> generateReport(Collection<? extends SalesData> sales) {
        var report = new HashMap<SalesData, Percentage>();
        sales.forEach(salesData -> report.put(salesData, percentageFor(salesData)));
        return report;
    }

    private Percentage percentageFor(SalesData salesData) {
        return Percentage.forData(salesData.getSales(), getTotalSales());
    }

    private double calculateTotalSales(Collection<? extends SalesData> sales) {
        return sales.stream()
                    .mapToDouble(SalesData::getSales)
                    .sum();
    }

    public double percentageOf(double value) {
        return Percentage.forData(value, getTotalSales()).getValue();
    }
}