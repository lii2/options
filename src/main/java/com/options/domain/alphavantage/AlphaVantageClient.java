package com.options.domain.alphavantage;

import com.options.entities.EmaData;
import com.options.entities.MacdData;
import com.options.entities.StockData;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class AlphaVantageClient {

    private AlphaVantageConnector connector;

    public AlphaVantageClient() {
        this.connector = new AlphaVantageConnector();
    }

    public List<StockData> getLast100DaysTimeSeriesData(String ticker) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        List<StockData> spyStockDataList = new ArrayList<>();
        String[] spyTimeSeriesArray = connector.getTimeSeriesDaily(ticker).split("\n");
        // First row is header so need to start at 1.
        for (int i = 1; i < spyTimeSeriesArray.length; i++) {
            spyStockDataList.add(new StockData(ticker, spyTimeSeriesArray[i].split(",")));
        }
        return spyStockDataList;
    }

    public List<EmaData> getLast100DaysEmaData(String ticker, String interval) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        List<EmaData> emaDataList = new ArrayList<>();
        String[] emaArray = connector.getEmaDaily(ticker, interval).split("\n");
        for (int i = 1; i < emaArray.length; i++) {
            emaDataList.add(new EmaData(ticker, emaArray[i].split(",")));
        }
        return emaDataList;
    }

    public List<MacdData> getMacdData(String ticker) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        List<MacdData> macdDataList = new ArrayList<>();
        String[] macdDataArray = connector.getMacd(ticker).split("\n");
        for (int i = 1; i < macdDataArray.length; i++) {
            macdDataList.add(new MacdData(ticker, macdDataArray[i].split(",")));
        }
        return macdDataList;
    }

}
