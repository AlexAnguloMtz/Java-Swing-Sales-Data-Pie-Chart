package com.aram.sales.model;

public class Percentage {

    private final double value;

    public Percentage(double value) {
        this.value = value;
    }

    public static Percentage forData(double value, double total) {
        double percentage = (value * 100) / total;
        return new Percentage(percentage);
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Percentage{" +
                "value=" + value +
                '}';
    }

}