package com.aram.sales.gui;

import com.aram.sales.model.SalesData;

import java.awt.*;

import static java.awt.Color.*;

public class ColorPicker {

    public Color colorFor(SalesData salesData) {
        Color color = null;
        switch (salesData.getDepartment()) {
            case APPLE_WATCH: color = new Color(247, 130, 0);
                break;
            case APP_STORE: color = new Color(151, 57, 153);
                break;
            case IPHONE: color = new Color(0, 156, 223);
                break;
            case MAC: color =  new Color(94, 189, 62);
                break;
            default :
                throw new IllegalArgumentException("Unrecognized chart portion name");
        }
        return color;
    }

}
