package com.options.operations;

import com.options.domain.data.DailyData;
import com.options.entities.EmaData;
import com.options.entities.StockData;
import com.options.repositories.EmaDataRepository;
import com.options.repositories.StockDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;

@Component
public class CalculateRecommendationOperation {

    @Autowired
    private StockDataRepository stockDataRepository;

    @Autowired
    private EmaDataRepository emaDataRepository;

    private int daysOfData;

    private static final BigDecimal TWO = new BigDecimal(2);

    private List<DailyData> dailyDataList;

    public CalculateRecommendationOperation() {
        this.daysOfData = 30;
    }

    public String execute() {
        setDataFromDatabase();
        return discoverCrossovers();
    }

    private String discoverCrossovers() {
        int lastDayIndex = dailyDataList.size() - 1;
        StringBuilder stringBuilder = new StringBuilder();
        BigDecimal previousEmaVar = new BigDecimal(0);
        BigDecimal currentEmaVar = null;

        // If EMA is above average, the comparison will return 1.

        // Need to understand trend, difference between long term and short term trend and decide based on trend
        // need algorithmn to determine trend
        // if the five crosses the twenty moving average
        // look at time intervals of trends to know likely intervals of upcoming trends.
        // Use 5 day and 20 day sma as points to buy and sell options

         /*
        calculate a 10 day interval of exponential moving average, and if the price rises above the EMA sell a one week put 100 points
        below the support. If the price drops below the EMA, sell a one week call 100 points above a resistance
        */
        for (int i = lastDayIndex - 1; i >= 0; i--) {
            currentEmaVar = calculateEmaVar(dailyDataList.get(i + 1), dailyDataList.get(i), previousEmaVar);
            if (dailyDataList.get(i).averagedBelowEma() != dailyDataList.get(i + 1).averagedBelowEma()) {
                stringBuilder.append("\nFound crossing on: ").append(dailyDataList.get(i).getDay()).append(" \n");
                if (dailyDataList.get(i).averagedBelowEma()) {
                    appendDropMessage(stringBuilder, dailyDataList.get(i + 1).openCloseMean(),
                            dailyDataList.get(i).openCloseMean(), dailyDataList.get(i + 1).getEma(),
                            dailyDataList.get(i).getEma());
                } else {
                    appendRiseMessage(stringBuilder, dailyDataList.get(i + 1).openCloseMean(),
                            dailyDataList.get(i).openCloseMean(), dailyDataList.get(i + 1).getEma(),
                            dailyDataList.get(i).getEma());
                }
                stringBuilder.append("Current variance is: ").append(currentEmaVar);
                previousEmaVar = currentEmaVar;
                previousEmaVar = previousEmaVar.setScale(2, RoundingMode.DOWN);
            }

            // Use volume and variance to determine strategy (Iron Condor, Strangle, Butterfly, etc)
            if (dailyDataList.get(i).getVolume().compareTo(dailyDataList.get(i + 1).getVolume()) > 0) {
                stringBuilder.append("\nVolume is increasing, the trend is strengthening").append("\n");
            } else {
                if (currentEmaVar.abs().compareTo(BigDecimal.ONE) < 0) {
                    stringBuilder.append("\nSomething is happening ").append(dailyDataList.get(i).getDay()).append("\n");
                }
            }
        }
        stringBuilder.append("\nCurrent EMA is: ").append(dailyDataList.get(daysOfData - 1).getEma());
        stringBuilder.append("\nCurrent Variance is: ").append(currentEmaVar);

        return stringBuilder.toString();
    }

    private void setDataFromDatabase() {
        EmaData[] last30DaysEmaData = emaDataRepository.getLastXDays(daysOfData).stream().toArray(EmaData[]::new);
        StockData[] last30DaysStockData = stockDataRepository.getLastXDays(daysOfData).stream().toArray(StockData[]::new);
        dailyDataList = DailyData.generateDailyData(last30DaysStockData, last30DaysEmaData);
    }

    private void appendRiseMessage(StringBuilder stringBuilder, BigDecimal yesterdaysStockPrice,
                                   BigDecimal todaysStockPrice, BigDecimal yesterdaysEma, BigDecimal todaysEma) {
        stringBuilder.append("price rose above the ema,\n    price was: ").append(yesterdaysStockPrice)
                .append("\n       ema is: ").append(todaysEma)
                .append("\n price is now: ").append(todaysStockPrice)
                .append("\n Recommendation: buy calls to sell or sell puts below current price.");
    }

    private void appendDropMessage(StringBuilder stringBuilder, BigDecimal yesterdaysStockPrice,
                                   BigDecimal todaysStockPrice, BigDecimal yesterdaysEma, BigDecimal todaysEma) {
        stringBuilder.append("           price dropped below the ema,\n    price was: ").append(yesterdaysStockPrice)
                .append("\n                  ema is: ").append(todaysEma)
                .append("\n            price is now: ").append(todaysStockPrice)
                .append("\n            Recommendation: buy puts to sell or sell calls below current price.");
    }

    public void setDaysOfData(int daysOfData) {
        this.daysOfData = daysOfData;
    }

    private BigDecimal calculateEmaVar(DailyData previousDay,
                                       DailyData currentDay, BigDecimal previousEmaVar) {
        BigDecimal delta = currentDay.openCloseMean().subtract(previousDay.getEma());
        BigDecimal alpha = currentDay.getEma().subtract(previousDay.getEma()).divide(delta, RoundingMode.DOWN);
        return (BigDecimal.ONE.subtract(alpha).multiply(previousEmaVar.add(alpha.multiply(delta.multiply(delta)))));
    }
}
