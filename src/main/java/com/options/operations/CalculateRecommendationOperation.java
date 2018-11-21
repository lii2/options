package com.options.operations;

import com.options.domain.alphavantage.AlphaVantageClient;
import com.options.entities.EmaData;
import com.options.entities.StockData;
import com.options.repositories.EmaDataRepository;
import com.options.repositories.StockDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.List;

@Component
public class CalculateRecommendationOperation {

    @Autowired
    StockDataRepository stockDataRepository;

    @Autowired
    EmaDataRepository emaDataRepository;

    AlphaVantageClient alphaVantageClient;

    private static final int DAYS_OF_DATA = 30;

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
                if (lastEmaData == null || lastEmaData.getEmaDataKey().getDay().before(emaData.getEmaDataKey().getDay())) {
                    emaDataRepository.save(emaData);
                }
            }
        }
    }

    private String discoverCrossovers() {
        setDataFromDatabase();
        BigDecimal bigDecimal = (last30DaysEmaData[DAYS_OF_DATA - 1].getEma());
        // TODO: FIX PRECISION PROBLEMS
        boolean previousDayClosedBelowEma =
                (last30DaysEmaData[DAYS_OF_DATA - 1].getEma().compareTo(last30DaysStockData[DAYS_OF_DATA - 1].getClose()) == 1);
        boolean closedBelowEma;

        // Need to understand trend, difference between long term and short term trend and decide based on trend
        // need algorithmn to determine trend
        // if the five crosses the twenty moving average
        StringBuilder stringBuilder = new StringBuilder();
        /*
        calculate a 10 day interval of exponential moving average, and if the price rises above the EMA sell a one week put 100 points
        below the support. If the price drops below the EMA, sell a one week call 100 points above a resistance
        */
        //TODO: Need to fix this logic
        for (int i = DAYS_OF_DATA - 2; i >= 0; i--) {
            closedBelowEma = (last30DaysEmaData[i].getEma().compareTo(last30DaysStockData[i].getClose()) == 1);
            if (closedBelowEma != previousDayClosedBelowEma) {
                stringBuilder.append("found crossing on: " + last30DaysStockData[i].getStockDataKey().getDay() + " \n");
                if (closedBelowEma)
                    appendRiseMessage(stringBuilder, last30DaysStockData[i + 1].getClose(), last30DaysEmaData[i + 1].getEma(),
                            last30DaysStockData[i].getClose(), last30DaysEmaData[i].getEma());
                else
                    appendDropMessage(stringBuilder, last30DaysStockData[i + 1].getClose(), last30DaysEmaData[i + 1].getEma(),
                            last30DaysStockData[i].getClose(), last30DaysEmaData[i].getEma());
            }
            previousDayClosedBelowEma = closedBelowEma;
        }
        return stringBuilder.toString();
    }

    private void setDataFromDatabase() {
        last30DaysEmaData = emaDataRepository.getLastThirtyDays().stream().toArray(EmaData[]::new);
        last30DaysStockData = stockDataRepository.getLastThirtyDays().stream().toArray(StockData[]::new);
    }

    private void appendRiseMessage(StringBuilder stringBuilder, BigDecimal yesterdaysStockPrice, BigDecimal yesterdaysEma,
                                   BigDecimal todaysStockPrice, BigDecimal todaysEma) {
        stringBuilder.append("price rose above the ema, price was: " + yesterdaysStockPrice +
                " and ema was " + yesterdaysEma +
                "\n price is now " + todaysStockPrice +
                " ema is now " + todaysEma + " sell puts.\n");
    }

    private void appendDropMessage(StringBuilder stringBuilder, BigDecimal yesterdaysStockPrice, BigDecimal yesterdaysEma,
                                   BigDecimal todaysStockPrice, BigDecimal todaysEma) {
        stringBuilder.append("price dropped below the ema, price was: " + yesterdaysStockPrice +
                " and ema was " + yesterdaysEma +
                "\n price is now " + todaysStockPrice +
                " ema is now " + todaysEma + " sell puts.\n");
    }
}
