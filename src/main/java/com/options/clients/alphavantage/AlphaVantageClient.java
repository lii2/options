package com.options.clients.alphavantage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@Component
public class AlphaVantageClient {

    private AlphaVantageConnector connector;

    @Autowired
    public AlphaVantageClient() {
        this.connector = new AlphaVantageConnector();
    }

    public String[] getLast100DaysTimeSeriesData(String ticker) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        String[] spyTimeSeriesArray = connector.getTimeSeriesDaily(ticker).replace("\r", "").split("\n");

        return spyTimeSeriesArray;
    }

    public String[] getLast100DaysEmaData(String ticker, String interval) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        String[] emaArray = connector.getEmaDaily(ticker, interval).replace("\r", "").split("\n");

        return emaArray;
    }

    public String[] getMacdData(String ticker) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        String[] macdDataArray = connector.getMacd(ticker).replace("\r", "").split("\n");

        return macdDataArray;
    }

    public String[] getBbandData(String ticker) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        String[] bbandDataArray = connector.getBbandsDaily(ticker).replace("\r", "").split("\n");

        return bbandDataArray;
    }

    public AlphaVantageDataPackage getAlphaVantageDataPackage(String tickerSymbol) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        String[] timeSeriesData = getLast100DaysTimeSeriesData(tickerSymbol);
        String[] emaData = getLast100DaysEmaData(tickerSymbol, "10");
        String[] macdData = getMacdData(tickerSymbol);
        String[] bbandData = getBbandData(tickerSymbol);

        return new AlphaVantageDataPackage(timeSeriesData, emaData, macdData, bbandData);
    }

}
