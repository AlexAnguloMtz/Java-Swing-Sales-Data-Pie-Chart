package com.aram.sales.model;

public enum SalesDepartment {

    APPLE_WATCH("Apple Watch"),
    APP_STORE("App Store"),
    IPHONE("iPhone"),
    MAC("Mac");

    private final String name;

    SalesDepartment(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
