package com.options.operations;

import com.options.domain.alphavantage.AlphaVantageClient;
import com.options.entities.EmaData;
import com.options.entities.StockData;
import com.options.repositories.EmaDataRepository;
import com.options.repositories.StockDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.List;

@Component
public class CalculateRecommendationOperation {

    @Autowired
    private StockDataRepository stockDataRepository;

    @Autowired
    private EmaDataRepository emaDataRepository;

    private AlphaVantageClient alphaVantageClient;

    private static final int DAYS_OF_DATA = 30;

    private static final BigDecimal TWO = new BigDecimal(2);

    private EmaData[] last30DaysEmaData;

    private StockData[] last30DaysStockData;

    public CalculateRecommendationOperation() {
        alphaVantageClient = new AlphaVantageClient();
    }

    public String execute() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, ParseException {
        getData();
        return discoverCrossovers();
    }

    private void getData() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, ParseException {
        smartPersist();
    }

    private void smartPersist() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, ParseException {
        StockData lastStockData = stockDataRepository.getLatestRecord();
        List<StockData> stockDataList = alphaVantageClient.getLast100DaysTimeSeriesData("SPY");
        if (lastStockData == null) {
            for (StockData stockData : stockDataList) {
                stockDataRepository.save(stockData);
            }
        } else {
            for (StockData stockData : stockDataList) {
                if (lastStockData.getStockDataKey().getDay().before(stockData.getStockDataKey().getDay())) {
                    stockDataRepository.save(stockData);
                }
            }
        }

        EmaData lastEmaData = emaDataRepository.getLatestRecord();
        // TODO: Check if data from today is already in database, if so, skip persistance
        if (lastEmaData == null) {
            List<EmaData> emaDataList = alphaVantageClient.getLast100DaysEmaData("SPY", "10");
            for (EmaData emaData : emaDataList) {
                emaDataRepository.save(emaData);
            }
        } else {
            List<EmaData> emaDataList = alphaVantageClient.getLast100DaysEmaData("SPY", "10");
            for (EmaData emaData : emaDataList) {
                if (lastEmaData.getEmaDataKey().getDay().before(emaData.getEmaDataKey().getDay())) {
                    emaDataRepository.save(emaData);
                }
            }
        }
    }

    private String discoverCrossovers() {
        setDataFromDatabase();
        BigDecimal previousOpenCloseAverage = last30DaysStockData[DAYS_OF_DATA - 1].getClose()
                .add(last30DaysStockData[DAYS_OF_DATA - 1].getOpen()).divide(TWO, RoundingMode.FLOOR);
        BigDecimal openCloseAverage;
        // If EMA is above open close average will be 1.
        boolean previousDayClosedBelowEma =
                (last30DaysEmaData[DAYS_OF_DATA - 1].getEma().compareTo(previousOpenCloseAverage) > 0);
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

        for (int i = DAYS_OF_DATA - 2; i >= 0; i--) {
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
        return stringBuilder.toString();
    }

    private void setDataFromDatabase() {
        last30DaysEmaData = emaDataRepository.getLastThirtyDays().stream().toArray(EmaData[]::new);
        last30DaysStockData = stockDataRepository.getLastThirtyDays().stream().toArray(StockData[]::new);
    }

    private void appendRiseMessage(StringBuilder stringBuilder, BigDecimal yesterdaysStockPrice,
                                   BigDecimal todaysStockPrice, BigDecimal todaysEma) {
        stringBuilder.append("\nprice rose above the ema,\n    price was: ").append(yesterdaysStockPrice)
                .append("\n price is now: ").append(todaysStockPrice)
                .append("\n ema is now ").append(todaysEma).append(" sell puts below current price or buy calls. \n");
    }

    private void appendDropMessage(StringBuilder stringBuilder, BigDecimal yesterdaysStockPrice,
                                   BigDecimal todaysStockPrice, BigDecimal todaysEma) {
        stringBuilder.append("\nprice dropped below the ema,\n    price was: ").append(yesterdaysStockPrice)
                .append("\n price is now: ").append(todaysStockPrice)
                .append("\n ema is now ").append(todaysEma).append(" sell calls below current price or buy puts. \n");
    }
}
