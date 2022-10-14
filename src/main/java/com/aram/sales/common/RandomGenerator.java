package com.aram.sales.common;

import java.util.Random;

public class RandomGenerator {

    private final Random random;

    public RandomGenerator() {
        this.random = new Random();
    }

    public int between(int low, int high) {
        return random.nextInt(high - low) + low;
    }
}
