package com.options.backtest;

import com.options.trend.Trend;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationResult {

    private static final BigDecimal INDIFFERENCE_LIMIT = new BigDecimal(0.05);

    private Outcome outcome;
    private Trend trend;
    private BigDecimal priceChange;
    private LocalDate recommendationDay;
    private int daysHeld;

    public RecommendationResult(int daysHeld, Trend trend, BigDecimal priceChange, LocalDate recommendationDay) {
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
        this.recommendationDay = recommendationDay;
        this.trend = trend;
        this.priceChange = priceChange;
        this.daysHeld = daysHeld;

    }

    @Override
    public String toString() {
        return "RecommendationResult{" +
                "recommendationDay=" + recommendationDay +
                "outcome=" + outcome +
                ", trend=" + trend +
                ", priceChange=" + priceChange +
                ", daysHeld=" + daysHeld +
                '}';
    }

}
