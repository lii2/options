package com.options.strategy;

import com.options.technicals.DailyTechnicals;
import com.options.recommendation.Recommendation;
import com.options.recommendation.Trend;

import java.math.BigDecimal;
import java.util.List;

public class EntranceStrategies {

    // TODO: Find a good way to determine this limit, empirically
    // 0.1 is best value so far w/ 90% accuracy, 0.2 only gives 95% and 0.05 gives 78%
    private static final BigDecimal MACD_HIST_LIMIT = new BigDecimal(0.1);

    public void findEntrance(DailyTechnicals daysData, List<Recommendation> pendingRecommendations) {
        emaCrossing(daysData, pendingRecommendations);
        leavingBollingerBandOutskirts(daysData, pendingRecommendations);
    }

    private void emaCrossing(DailyTechnicals daysData, List<Recommendation> pendingRecommendations) {
        if (daysData.averagedBelowEma() != daysData.getPreviousDaysData().averagedBelowEma()
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

    private String generateRiseMessage(DailyTechnicals dailyTechnicals) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" price rose above the ema, price was: ").append(dailyTechnicals.getPreviousDaysData().getOpenCloseMean())
                .append(" ema is: ").append(dailyTechnicals.getEma() )
                .append(" price is now: ").append(dailyTechnicals.getOpenCloseMean() )
                .append(" Message: buy calls to sell.");
        return stringBuilder.toString();
    }

    private String generateDropMessage(DailyTechnicals dailyTechnicals) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" price dropped below the ema, price was: ").append(dailyTechnicals.getPreviousDaysData().getOpenCloseMean())
                .append(" ema is: ").append(dailyTechnicals.getEma())
                .append(" price is now: ").append(dailyTechnicals.getOpenCloseMean())
                .append(" Message: buy puts to sell.");
        return stringBuilder.toString();
    }

    private void leavingBollingerBandOutskirts(DailyTechnicals dailyTechnicals, List<Recommendation> pendingRecommendations) {
        if (dailyTechnicals.getPreviousDaysData().getBoxHigh().compareTo(dailyTechnicals.getPreviousDaysData().getRealUpperBand()) > 0
                && dailyTechnicals.getBoxHigh().compareTo(dailyTechnicals.getRealUpperBand()) < 0) {
            Recommendation recommendation = new Recommendation(Trend.BEARISH, "Buy puts, Ticker leaving overbought territory",
                    dailyTechnicals);
            pendingRecommendations.add(recommendation);
        } else if (dailyTechnicals.getPreviousDaysData().getBoxLow().compareTo(dailyTechnicals.getPreviousDaysData().getRealLowerBand()) < 0
                && dailyTechnicals.getBoxLow().compareTo(dailyTechnicals.getRealLowerBand()) > 0) {
            Recommendation recommendation = new Recommendation(Trend.BULLISH, "Buy calls, Ticker leaving oversold territory",
                    dailyTechnicals);
            pendingRecommendations.add(recommendation);
        }
    }
}