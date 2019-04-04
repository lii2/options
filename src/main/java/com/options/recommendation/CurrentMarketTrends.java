package com.options.recommendation;

import lombok.Data;

@Data
public class CurrentMarketTrends {

    private Trend hundredDayTrend;

    private Trend fiftyDayTrend;

    private Trend tenDayTrend;

    public CurrentMarketTrends(Trend hundredDayTrend, Trend fiftyDayTrend, Trend tenDayTrend) {
        this.hundredDayTrend = hundredDayTrend;
        this.fiftyDayTrend = fiftyDayTrend;
        this.tenDayTrend = tenDayTrend;
    }

    public boolean supports(Trend trend) {
        return ((hundredDayTrend.equals(trend) && fiftyDayTrend.equals(trend) && tenDayTrend.equals(trend))
                || (hundredDayTrend.equals(trend) && fiftyDayTrend.equals(trend))
                || (hundredDayTrend.equals(trend) && tenDayTrend.equals(trend))
                || (fiftyDayTrend.equals(trend) && tenDayTrend.equals(trend)));
    }
}
