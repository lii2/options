package com.options.operations;

import com.options.domain.alphavantage.AlphaVantageClient;
import com.options.entities.EmaData;
import com.options.entities.StockData;
import com.options.repositories.EmaDataRepository;
import com.options.repositories.StockDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public CalculateRecommendationOperation() {
        alphaVantageClient = new AlphaVantageClient();
    }

    public String execute() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, ParseException {
        getData();
        return recommend();
    }

    private void getData() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, ParseException {
        smartPersist();
        discoverCrossovers();
    }

    private String recommend() {
        return "Not yet implemented";
    }

    private void smartPersist() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, ParseException {
        StockData lastStockData = stockDataRepository.getLatestRecord();
        List<StockData> stockDataList = alphaVantageClient.getLast100DaysTimeSeriesData("SPY");
        for (StockData stockData : stockDataList) {
            if (lastStockData.getStockDataKey().getDay().before(stockData.getStockDataKey().getDay())) {
                stockDataRepository.save(stockData);
            }
        }

        EmaData lastEmaData = emaDataRepository.getLatestRecord();
        List<EmaData> emaDataList = alphaVantageClient.getLast100DaysEmaData("SPY", "10");
        for (EmaData emaData : emaDataList) {
            if (lastEmaData.getEmaDataKey().getDay().before(emaData.getEmaDataKey().getDay())) {
                emaDataRepository.save(emaData);
            }
        }
    }

    private void discoverCrossovers() {
        EmaData[] last30DaysEmaData = emaDataRepository.getLastThirtyDays().stream().toArray(EmaData[]::new);
        StockData[] last30DaysStockData = stockDataRepository.getLastThirtyDays().stream().toArray(StockData[]::new);
        boolean previousDayClosedBelowEma = (last30DaysEmaData[29].getEma() > last30DaysStockData[29].getClose());
        boolean closedBelowEma = false;
        /*
        calculate a 10 day interval of exponential moving average, and if the price rises above the EMA sell a one week put 100 points
        below the support. If the price drops below the EMA, sell a one week call 100 points above a resistance
        */
        //TODO: Need to fix this logic
        for (int i = 28; i >= 0; i--) {
            closedBelowEma = (last30DaysEmaData[i].getEma() > last30DaysStockData[i].getClose());
            if (closedBelowEma != previousDayClosedBelowEma) {
                System.out.println("found crossing: " + last30DaysEmaData[i] + " and " + last30DaysStockData[i]);
                if (closedBelowEma)
                    System.out.println("price rose above ema, price was: " + last30DaysStockData[i + 1].getClose() +
                            " and ema was " + last30DaysEmaData[i + 1].getEma() +
                            " price is now " + last30DaysStockData[i].getClose() +
                            " ema is now " + last30DaysEmaData[1].getEma() + " sell puts.");
                else
                    System.out.println("price dropped below ema, price was: " + last30DaysStockData[i + 1].getClose() +
                            " and ema was " + last30DaysEmaData[i + 1].getEma() +
                            " price is now " + last30DaysStockData[i].getClose() +
                            " ema is now " + last30DaysEmaData[1].getEma() + " sell calls.");
            }
            previousDayClosedBelowEma = closedBelowEma;
        }
    }
}
