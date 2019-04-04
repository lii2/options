package com.options.recommendation;

import java.math.BigDecimal;

public enum Trend {
    BEARISH,
    BULLISH;

    public static Trend getTrendUsingDifference(BigDecimal difference){
        if(difference.compareTo(BigDecimal.ZERO) > 0){
            return BULLISH;
        }else{
            return BEARISH;
        }
    }
}
