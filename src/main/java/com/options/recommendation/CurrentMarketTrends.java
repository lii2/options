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
}
