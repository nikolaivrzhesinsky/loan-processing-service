package com.example.loanservice.service.utilService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class RandomGeneratorService {

    public static Double generateDoubleNumBound(double lowerBound, double upperBound, int decimalPlaces) {

        final double dbl =
                new Random().nextDouble() * (upperBound - lowerBound) + lowerBound;
        BigDecimal bd = BigDecimal.valueOf(dbl)
                .setScale(decimalPlaces, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
