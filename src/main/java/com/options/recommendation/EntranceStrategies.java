package com.options.recommendation;

import com.options.data.DailyData;

import java.math.BigDecimal;
import java.util.List;

public class EntranceStrategies {

    // TODO: Find a good way to determine this limit, empirically
    // 0.1 is best value so far w/ 90% accuracy, 0.2 only gives 95% and 0.05 gives 78%
    private static final BigDecimal MACD_HIST_LIMIT = new BigDecimal(0.1);

    public void findEntrance(DailyData daysData, List<Recommendation> pendingRecommendations) {
        emaCrossing(daysData, pendingRecommendations);
        leavingBollingerBandOutskirts(daysData, pendingRecommendations);
    }

    private void emaCrossing(DailyData daysData, List<Recommendation> pendingRecommendations) {
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

    private String generateRiseMessage(DailyData dailyData) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" price rose above the ema, price was: ").append(dailyData.getPreviousDaysData().getOpenCloseMean())
                .append(" ema is: ").append(dailyData.getEma() )
                .append(" price is now: ").append(dailyData.getOpenCloseMean() )
                .append(" Message: buy calls to sell.");
        return stringBuilder.toString();
    }

    private String generateDropMessage(DailyData dailyData) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" price dropped below the ema, price was: ").append(dailyData.getPreviousDaysData().getOpenCloseMean())
                .append(" ema is: ").append(dailyData.getEma())
                .append(" price is now: ").append(dailyData.getOpenCloseMean())
                .append(" Message: buy puts to sell.");
        return stringBuilder.toString();
    }

    private void leavingBollingerBandOutskirts(DailyData dailyData, List<Recommendation> pendingRecommendations) {
        if (dailyData.getPreviousDaysData().getBoxHigh().compareTo(dailyData.getPreviousDaysData().getRealUpperBand()) > 0
                && dailyData.getBoxHigh().compareTo(dailyData.getRealUpperBand()) < 0) {
            Recommendation recommendation = new Recommendation(Trend.BEARISH, "Buy puts, Ticker leaving overbought territory",
                    dailyData);
            pendingRecommendations.add(recommendation);
        } else if (dailyData.getPreviousDaysData().getBoxLow().compareTo(dailyData.getPreviousDaysData().getRealLowerBand()) < 0
                && dailyData.getBoxLow().compareTo(dailyData.getRealLowerBand()) > 0) {
            Recommendation recommendation = new Recommendation(Trend.BULLISH, "Buy calls, Ticker leaving oversold territory",
                    dailyData);
            pendingRecommendations.add(recommendation);
        }
    }
}