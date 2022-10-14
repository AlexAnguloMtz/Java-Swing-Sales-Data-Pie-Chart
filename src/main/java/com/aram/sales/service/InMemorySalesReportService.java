package com.aram.sales.service;

import com.aram.sales.common.RandomGenerator;
import com.aram.sales.model.SalesData;
import com.aram.sales.model.SalesDepartment;
import com.aram.sales.model.SalesReport;

import java.util.Collection;
import java.util.Set;

import static com.aram.sales.model.SalesDepartment.*;

public class InMemorySalesReportService implements SalesReportService {

    private static final int MIN_SALES = 100;
    private static final int MAX_SALES = 1500;

    private final RandomGenerator random;

    public InMemorySalesReportService() {
        this.random = new RandomGenerator();
    }

    @Override
    public SalesReport getSalesReport() {
        return new SalesReport(allSalesData());
    }

    private Collection<? extends SalesData> allSalesData() {
        return Set.of(
                new SalesData(APPLE_WATCH, randomSalesNumber()),
                new SalesData(APP_STORE, randomSalesNumber()),
                new SalesData(IPHONE, randomSalesNumber()),
                new SalesData(MAC, randomSalesNumber())
        );
    }

    private int randomSalesNumber() {
        return random.between(MIN_SALES, MAX_SALES + 1);
    }

}
