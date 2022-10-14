package com.aram.sales.gui;

import java.awt.*;

class ChartPortion {

    private final double value;
    private final Color color;
    private final String name;

    public ChartPortion(double value, Color color, String name) {
        this.value = value;
        this.color = color;
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }
}