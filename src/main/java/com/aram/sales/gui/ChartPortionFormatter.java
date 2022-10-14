package com.aram.sales.gui;

public class ChartPortionFormatter {

    public String format(ChartPortion portion, double percentage) {
        return String.format("%s $%s (%.2f%%)", portion.getName(), portion.getValue(), percentage);
    }

}
