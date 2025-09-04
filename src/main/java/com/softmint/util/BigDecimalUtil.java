package com.softmint.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtil {
    public static BigDecimal ceilToWholeNumber(BigDecimal value) {
        if (value != null) {
            return value.setScale(0, RoundingMode.CEILING); // Ceil to next whole number
        }
        return value;
    }
}

