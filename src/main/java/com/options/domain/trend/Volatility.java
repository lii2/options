package com.options.domain.trend;

import java.math.BigDecimal;

import static com.options.domain.DomainConstants.*;

public enum Volatility {
    NOT_VOLATILE,
    VOLATILE,
    VERY_VOLATILE,
    EXTREMELY_VOLATILE;

    public static Volatility determineVolatility(BigDecimal emaVariance) {
        if (emaVariance.abs().compareTo(BIG_DECIMAL_POINT_FIVE) < 0) {
            return NOT_VOLATILE;
        } else if (emaVariance.abs().compareTo(BigDecimal.ONE) < 0) {
            return VOLATILE;
        } else if (emaVariance.abs().compareTo(BIG_DECIMAL_TWO) < 0) {
            return VERY_VOLATILE;
        } else if (emaVariance.abs().compareTo(BIG_DECIMAL_TWO) > 0) {
            return EXTREMELY_VOLATILE;
        }

        throw new TrendException("Could not compute volatility");
    }
}
