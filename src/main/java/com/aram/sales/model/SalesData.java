package com.aram.sales.model;

import java.util.Objects;

import static java.lang.Double.compare;

public class SalesData implements Comparable<SalesData> {

    private final SalesDepartment department;
    private final double sales;

    public SalesData(SalesDepartment department, int sales) {
        this.department = department;
        this.sales = sales;
    }

    public String getDepartmentName() {
        return department.getName();
    }

    public SalesDepartment getDepartment() {
        return department;
    }

    public double getSales() {
        return sales;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalesData salesData = (SalesData) o;
        return compare(salesData.sales, sales) == 0 && Objects.equals(department, salesData.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(department, sales);
    }

    @Override
    public String toString() {
        return "SalesData{" +
                "department='" + department + '\'' +
                ", sales=" + sales +
                '}';
    }

    @Override
    public int compareTo(SalesData other) {
        return compare(sales, other.sales);
    }

}