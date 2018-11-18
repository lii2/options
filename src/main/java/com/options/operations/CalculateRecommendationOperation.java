package com.options.operations;

import com.options.domain.alphavantage.AlphaVantageClient;
import com.options.entities.StockData;
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
    }

    private String recommend() {
        return "Not yet implemented";
    }

    private void smartPersist() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, ParseException {
        List<StockData> stockDataList = alphaVantageClient.getLast100DaysStockData("SPY");
        for (StockData stockData : stockDataList) {
            stockDataRepository.save(stockData);
        }

    }

    private boolean missingData() {
        return false;
    }
}
