package com.options.operations.analysis;

import com.options.domain.choice.Recommendation;
import com.options.domain.data.DailyData;
import com.options.domain.trend.Trend;

import java.math.BigDecimal;
import java.util.List;

public class EntranceStrategies {

    // TODO: Find a good way to determine this limit, empirically
    // 0.1 is best value so far w/ 90% accuracy, 0.2 only gives 95% and 0.05 gives 78%
    private static final BigDecimal MACD_HIST_LIMIT = new BigDecimal(0.1);

    public void findEntrance(DailyData daysData, List<Recommendation> pendingRecommendations) {
        if (priceCrossedOverEma(daysData)
                && daysData.getMacdHist().abs().compareTo(MACD_HIST_LIMIT) > 0) {
            if (daysData.averagedBelowEma()) {
                Recommendation recommendation = new Recommendation(Trend.BEARISH, generateDropMessage(daysData),
                        daysData);
                pendingRecommendations.add(recommendation);
            } else {
                Recommendation recommendation = new Recommendation(Trend.BULLISH, generateRiseMessage(daysData),
                        daysData);
                pendingRecommendations.add(recommendation);
            }
        }
    }

    private String generateRiseMessage(DailyData dailyData) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nprice rose above the ema, price was: ").append(dailyData.getPreviousDaysData().getOpenCloseMean())
                .append("\nema is: ").append(dailyData.getEma())
                .append("\nprice is now: ").append(dailyData.getOpenCloseMean())
                .append("\nMessage: buy calls to sell.");
        return stringBuilder.toString();
    }

    private String generateDropMessage(DailyData dailyData) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nprice dropped below the ema, price was: ").append(dailyData.getPreviousDaysData().getOpenCloseMean())
                .append("\nema is: ").append(dailyData.getEma())
                .append("\nprice is now: ").append(dailyData.getOpenCloseMean())
                .append("\nMessage: buy puts to sell.");
        return stringBuilder.toString();
    }

    private boolean priceCrossedOverEma(DailyData dailyData) {
        return dailyData.averagedBelowEma() != dailyData.getPreviousDaysData().averagedBelowEma();
    }
}
