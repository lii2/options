package com.options.operations;

import com.options.entities.EmaData;
import com.options.entities.StockData;
import com.options.repositories.EmaDataRepository;
import com.options.repositories.StockDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class CalculateRecommendationOperation {

    @Autowired
    private StockDataRepository stockDataRepository;

    @Autowired
    private EmaDataRepository emaDataRepository;

    private int daysOfData;

    private static final BigDecimal TWO = new BigDecimal(2);

    private EmaData[] last30DaysEmaData;

    private StockData[] last30DaysStockData;

    public CalculateRecommendationOperation() {
        this.daysOfData = 30;
    }

    public String execute() {
        return discoverCrossovers();
    }

    private String discoverCrossovers() {
        setDataFromDatabase();
        BigDecimal previousOpenCloseAverage = last30DaysStockData[daysOfData - 1].getClose()
                .add(last30DaysStockData[daysOfData - 1].getOpen()).divide(TWO, RoundingMode.FLOOR);
        BigDecimal openCloseAverage;
        // If EMA is above open close average will be 1.
        boolean previousDayClosedBelowEma =
                (last30DaysEmaData[daysOfData - 1].getEma().compareTo(previousOpenCloseAverage) > 0);
        boolean closedBelowEma;

        // Need to understand trend, difference between long term and short term trend and decide based on trend
        // need algorithmn to determine trend
        // if the five crosses the twenty moving average
        // look at time intervals of trends to know likely intervals of upcoming trends.
        StringBuilder stringBuilder = new StringBuilder();
        /*
        calculate a 10 day interval of exponential moving average, and if the price rises above the EMA sell a one week put 100 points
        below the support. If the price drops below the EMA, sell a one week call 100 points above a resistance
        */
        for (int i = daysOfData - 2; i >= 0; i--) {
            openCloseAverage = last30DaysStockData[i].getClose().add(last30DaysStockData[i].getOpen()).divide(TWO, RoundingMode.FLOOR);
            closedBelowEma = (last30DaysEmaData[i].getEma().compareTo(openCloseAverage) > 0);
            if (closedBelowEma != previousDayClosedBelowEma) {
                stringBuilder.append("\nFound crossing on: ").append(last30DaysStockData[i].getStockDataKey().getDay()).append(" \n");
                if (closedBelowEma)
                    appendDropMessage(stringBuilder, previousOpenCloseAverage,
                            openCloseAverage, last30DaysEmaData[i].getEma());
                else
                    appendRiseMessage(stringBuilder, previousOpenCloseAverage,
                            openCloseAverage, last30DaysEmaData[i].getEma());
            }
            previousDayClosedBelowEma = closedBelowEma;
            previousOpenCloseAverage = openCloseAverage;
        }

        stringBuilder.append("\nCurrent EMA is: ").append(last30DaysEmaData[0].getEma());
        return stringBuilder.toString();
    }

    private void setDataFromDatabase() {
        last30DaysEmaData = emaDataRepository.getLastXDays(daysOfData).stream().toArray(EmaData[]::new);
        last30DaysStockData = stockDataRepository.getLastXDays(daysOfData).stream().toArray(StockData[]::new);
    }

    private void appendRiseMessage(StringBuilder stringBuilder, BigDecimal yesterdaysStockPrice,
                                   BigDecimal todaysStockPrice, BigDecimal todaysEma) {
        stringBuilder.append("price rose above the ema,\n    price was: ").append(yesterdaysStockPrice)
                .append("\n       ema is: ").append(todaysEma)
                .append("\n price is now: ").append(todaysStockPrice)
                .append("\n Recommendation: buy calls to sell or sell puts below current price.\n");
    }

    private void appendDropMessage(StringBuilder stringBuilder, BigDecimal yesterdaysStockPrice,
                                   BigDecimal todaysStockPrice, BigDecimal todaysEma) {
        stringBuilder.append("price dropped below the ema,\n    price was: ").append(yesterdaysStockPrice)
                .append("\n       ema is: ").append(todaysEma)
                .append("\n price is now: ").append(todaysStockPrice)
                .append("\n Recommendation: buy puts to sell or sell calls below current price.\n");
    }

    public int getDaysOfData() {
        return daysOfData;
    }

    public void setDaysOfData(int daysOfData) {
        this.daysOfData = daysOfData;
    }
}
