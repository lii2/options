package com.options.domain.alphavantage;

import com.options.entities.StockData;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class AlphaVantageClient {

    private AlphaVantageConnector connector;

    public AlphaVantageClient() {
        this.connector = new AlphaVantageConnector();
    }

    public List<StockData> getLast100DaysStockData(String ticker) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, ParseException {
        List<StockData> spyStockDataList = new ArrayList<>();
        AlphaVantageConnector alphaVantageConnector = new AlphaVantageConnector();
        String[] spyTimeSeriesArray = alphaVantageConnector.getTimeSeriesDaily(ticker).split("\n");

        for (int i = 1; i < spyTimeSeriesArray.length; i++) {
            StockData StockData = new StockData(ticker, spyTimeSeriesArray[i].split(","));
            spyStockDataList.add(StockData);
        }
        return spyStockDataList;
    }

}
