package com.romanso.montyhallproblem.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Util {

    public static double round(double d, int digits) {
        if (digits < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(d);
        bd = bd.setScale(digits, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
