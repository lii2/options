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
    }

    private String recommend() {
        return "Not yet implemented";
    }

    private void smartPersist() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, ParseException {
        StockData lastStockData = stockDataRepository.getLatestRecord();
        List<StockData> stockDataList = alphaVantageClient.getLast100DaysTimeSeriesData("SPY");
        for (StockData stockData : stockDataList) {
            if (lastStockData.getStockDataKey().getDay().before(stockData.getStockDataKey().getDay())){
                stockDataRepository.save(stockData);
            }
        }

        EmaData lastEmaData = emaDataRepository.getLatestRecord();
        List<EmaData> emaDataList = alphaVantageClient.getLast100DaysEmaData("SPY", "10");
        for(EmaData emaData : emaDataList){
            if(lastEmaData.getEmaDataKey().getDay().before(emaData.getEmaDataKey().getDay())){
                emaDataRepository.save(emaData);
            }
        }

    }

    private boolean missingData() {
        return false;
    }
}
