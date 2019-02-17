package com.options.domain.alphavantage;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

public class AlphaVantageClient {

    private AlphaVantageConnector connector;

    public AlphaVantageClient() {
        this.connector = new AlphaVantageConnector();
    }

    public String[] getLast100DaysTimeSeriesData(String ticker) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        String[] spyTimeSeriesArray = connector.getTimeSeriesDaily(ticker).replace("\r","").split("\n");

        return spyTimeSeriesArray;
    }

    public String[] getLast100DaysEmaData(String ticker, String interval) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        String[] emaArray = connector.getEmaDaily(ticker, interval).replace("\r","").split("\n");

        return emaArray;
    }

    public String[] getMacdData(String ticker) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        String[] macdDataArray = connector.getMacd(ticker).replace("\r","").split("\n");

        return macdDataArray;
    }

    public String[] getBbandData(String ticker) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        String[] bbandDataArray = connector.getBbandsDaily(ticker).replace("\r","").split("\n");

        return bbandDataArray;
    }


}
