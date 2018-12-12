package com.options.operations;

import com.options.domain.choice.Recommendation;
import com.options.domain.data.DailyData;
import com.options.domain.trend.Trend;
import com.options.domain.trend.Volatility;
import com.options.entities.EmaData;
import com.options.entities.StockData;
import com.options.repositories.EmaDataRepository;
import com.options.repositories.StockDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Component
public class AnalyzeDataOperation {

    @Autowired
    private StockDataRepository stockDataRepository;

    @Autowired
    private EmaDataRepository emaDataRepository;

    private int daysOfData;

    private List<DailyData> dailyDataList;

    public AnalyzeDataOperation() {
        this.daysOfData = 30;
    }

    public List<Recommendation> execute(String ticker) {
        setDataFromDatabase(ticker);
        return doAnalysis();
    }

    private List<Recommendation> doAnalysis() {
        List<Recommendation> recommendations = new ArrayList<>();
        int lastDayIndex = dailyDataList.size() - 1;
        BigDecimal previousEmaVar = new BigDecimal(0);
        BigDecimal currentEmaVar = null;

        // If EMA is above average, the comparison will return 1.

        // Need to understand trend, difference between long term and short term trend and decide based on trend
        // need algorithmn to determine trend
        // if the five crosses the twenty moving average
        // look at time intervals of trends to know likely intervals of upcoming trends.
        // Use 5 day and 20 day sma as points to buy and sell options

         /*
        calculate a 10 day interval of exponential moving average,
        and if the price rises above the EMA sell a one week put 100 points
        below the support. If the price drops below the EMA, sell a one week call 100 points above a resistance
        */
        // TODO: FIND A WAY TO INDICATE WHEN TO SELL IRON CONDORS
        for (int i = lastDayIndex - 1; i >= 0; i--) {
            currentEmaVar = calculateEmaVar(dailyDataList.get(i), previousEmaVar);
            // Needs a little volatility so filtering out all the cases where it isn't volatile.
            if (priceCrossedOverEma(dailyDataList.get(i))
                    && !Volatility.determineVolatility(currentEmaVar).equals(Volatility.NOT_VOLATILE)) {
                if (dailyDataList.get(i).averagedBelowEma()) {
                    Recommendation recommendation = new Recommendation(Trend.BEARISH, generateDropMessage(dailyDataList.get(i)),
                            dailyDataList.get(i));
                    recommendations.add(recommendation);
                } else {
                    Recommendation recommendation = new Recommendation(Trend.BULLISH, generateRiseMessage(dailyDataList.get(i)),
                            dailyDataList.get(i));
                    recommendations.add(recommendation);
                }
                previousEmaVar = currentEmaVar;
                previousEmaVar = previousEmaVar.setScale(2, RoundingMode.DOWN);
            }

            // Use volume and variance to determine strategy (Iron Condor, Strangle, Butterfly, etc)
            // Determine volatility and if volatility is extreme don't do iron condor or butterfly do strangles
//            if (dailyDataList.get(i).getVolume().compareTo(dailyDataList.get(i + 1).getVolume()) > 0) {
//                  stringBuilder.append("\nVolume is increasing, the trend is strengthening").append("\n");
//            } else {
//                if (currentEmaVar.abs().compareTo(BigDecimal.ONE) < 0) {
//                         stringBuilder.append("\nSomething is happening ").append(dailyDataList.get(i).getDay()).append("\n");
//                }
//            }
        }

        return recommendations;
    }

    private void setDataFromDatabase(String ticker) {
        EmaData[] last30DaysEmaData = emaDataRepository.getLastXDays(ticker, daysOfData).stream().toArray(EmaData[]::new);
        StockData[] last30DaysStockData = stockDataRepository.getLastXDays(ticker, daysOfData).stream().toArray(StockData[]::new);
        dailyDataList = DailyData.generateDailyData(last30DaysStockData, last30DaysEmaData);
    }

    private String generateRiseMessage(DailyData dailyData) {
        StringBuilder string = new StringBuilder();
        string.append("\nprice rose above the ema, price was: ").append(dailyData.getPreviousDaysData().openCloseMean())
                .append("\nema is: ").append(dailyData.getEma())
                .append("\nprice is now: ").append(dailyData.openCloseMean())
                .append("\nMessage: buy calls to sell.");
        return string.toString();
    }

    private String generateDropMessage(DailyData dailyData) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nprice dropped below the ema, price was: ").append(dailyData.getPreviousDaysData().openCloseMean())
                .append("\nema is: ").append(dailyData.getEma())
                .append("\nprice is now: ").append(dailyData.openCloseMean())
                .append("\nMessage: buy puts to sell.");
        return stringBuilder.toString();
    }

    public void setDaysOfData(int daysOfData) {
        this.daysOfData = daysOfData;
    }

    private BigDecimal calculateEmaVar(DailyData currentDay, BigDecimal previousEmaVar) {
        BigDecimal delta = currentDay.openCloseMean().subtract(currentDay.getPreviousDaysData().getEma());
        BigDecimal alpha = currentDay.getEma().subtract(currentDay.getPreviousDaysData().getEma()).divide(delta, RoundingMode.DOWN);
        return (BigDecimal.ONE.subtract(alpha).multiply(previousEmaVar.add(alpha.multiply(delta.multiply(delta)))));
    }

    private boolean priceCrossedOverEma(DailyData dailyData) {
        return dailyData.averagedBelowEma() != dailyData.getPreviousDaysData().averagedBelowEma();
    }
}
