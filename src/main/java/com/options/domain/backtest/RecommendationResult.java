package com.options.domain.backtest;

import com.options.domain.trend.Trend;

import java.math.BigDecimal;

public class RecommendationResult {

    private static final BigDecimal INDIFFERENCE_LIMIT = new BigDecimal(0.05);

    private Outcome outcome;

    private Trend trend;

    private BigDecimal priceChange;

    private int daysHeld;

    public RecommendationResult() {
    }

    public RecommendationResult(int daysHeld, Trend trend, BigDecimal priceChange) {
        if (priceChange.abs().compareTo(INDIFFERENCE_LIMIT) > 0) {
            switch (trend) {
                case BEARISH:
                    if (priceChange.compareTo(BigDecimal.ZERO) < 0)
                        outcome = Outcome.FAILURE;
                    else
                        outcome = Outcome.SUCCESS;
                    break;
                case BULLISH:
                    if (priceChange.compareTo(BigDecimal.ZERO) > 0)
                        outcome = Outcome.FAILURE;
                    else
                        outcome = Outcome.SUCCESS;
                    break;
            }
        } else {
            outcome = Outcome.INDIFFERENT;
        }
        this.trend = trend;
        this.priceChange = priceChange;
        this.daysHeld = daysHeld;

    }


    public Outcome getOutcome() {
        return outcome;
    }

    public void setOutcome(Outcome outcome) {
        this.outcome = outcome;
    }

    public Trend getTrend() {
        return trend;
    }

    public void setTrend(Trend trend) {
        this.trend = trend;
    }

    public BigDecimal getPriceChange() {
        return priceChange;
    }

    public void setPriceChange(BigDecimal priceChange) {
        this.priceChange = priceChange;
    }

    public int getDaysHeld() {
        return daysHeld;
    }

    public void setDaysHeld(int daysHeld) {
        this.daysHeld = daysHeld;
    }

    @Override
    public String toString() {
        return "RecommendationResult{" +
                "outcome=" + outcome +
                ", trend=" + trend +
                ", priceChange=" + priceChange +
                ", daysHeld=" + daysHeld +
                '}';
    }
}
