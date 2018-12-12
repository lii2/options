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
public class SmartPersistOperation {

    @Autowired
    private StockDataRepository stockDataRepository;

    @Autowired
    private EmaDataRepository emaDataRepository;

    private AlphaVantageClient alphaVantageClient;

    public SmartPersistOperation() {
        alphaVantageClient = new AlphaVantageClient();
    }

    public String execute() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, ParseException {
        return getData();
    }

    private String getData() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, ParseException {
        return smartPersist();
    }

    private String smartPersist() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, ParseException {
        StringBuilder result = new StringBuilder();
        StockData lastStockData = stockDataRepository.getLatestRecord();
        List<StockData> stockDataList = alphaVantageClient.getLast100DaysTimeSeriesData("SPY");
        if (lastStockData == null) {
            for (StockData stockData : stockDataList) {
                stockDataRepository.save(stockData);
                result.append(stockData.getStockDataKey().getDay()).append(" data saved.\n");
            }
        } else {
            for (StockData stockData : stockDataList) {
                if (lastStockData.getStockDataKey().getDay().before(stockData.getStockDataKey().getDay())) {
                    stockDataRepository.save(stockData);
                    result.append(stockData.getStockDataKey().getDay()).append(" data saved.\n");
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

        return result.toString();
    }
}
