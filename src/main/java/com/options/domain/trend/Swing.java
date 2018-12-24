package com.options.domain.trend;

import java.math.BigDecimal;

import static com.options.domain.trend.ImportantNumbers.BIG_DECIMAL_POINT_FIVE;
import static com.options.domain.trend.ImportantNumbers.BIG_DECIMAL_TWO;

public enum Swing {


    // Heuristic for determining how fast the stock will move based on variance.
    NOT_SWINGING,
    SWINGING,
    SWINGING_A_LOT,
    EXTREMELY_SWINGY;

    public static Swing determineSwing(BigDecimal emaVariance) {
        if (emaVariance.abs().compareTo(BIG_DECIMAL_POINT_FIVE) < 0) {
            return NOT_SWINGING;
        } else if (emaVariance.abs().compareTo(BigDecimal.ONE) < 0) {
            return SWINGING;
        } else if (emaVariance.abs().compareTo(BIG_DECIMAL_TWO) < 0) {
            return SWINGING_A_LOT;
        } else if (emaVariance.abs().compareTo(BIG_DECIMAL_TWO) > 0) {
            return EXTREMELY_SWINGY;
        }

        throw new TrendException("Could not compute swing");
    }
}
