package com.options.operations;

import com.options.domain.alphavantage.AlphaVantageClient;
import com.options.entities.EmaData;
import com.options.entities.MacdData;
import com.options.entities.StockData;
import com.options.repositories.EmaDataRepository;
import com.options.repositories.MacdDataRepository;
import com.options.repositories.StockDataRepository;
import io.micrometer.core.instrument.util.StringUtils;
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

    @Autowired
    private MacdDataRepository macdDataRepository;

    private AlphaVantageClient alphaVantageClient;

    public SmartPersistOperation() {
        alphaVantageClient = new AlphaVantageClient();
    }

    public String execute(String ticker) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, ParseException {
        return getData(ticker);
    }

    private String getData(String ticker) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, ParseException {
        return smartPersist(ticker);
    }

    private String smartPersist(String ticker) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, ParseException {
        StringBuilder result = new StringBuilder();
        // TODO: Check if data from today is already in database, if so, skip persistance
        if (StringUtils.isNotBlank(ticker)) {
            ticker = ticker.toUpperCase();
            StockData lastStockData = stockDataRepository.getLatestRecord(ticker);
            List<StockData> stockDataList = alphaVantageClient.getLast100DaysTimeSeriesData(ticker);
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

            EmaData lastEmaData = emaDataRepository.getLatestRecord(ticker);
            List<EmaData> emaDataList = alphaVantageClient.getLast100DaysEmaData(ticker, "10");
            if (lastEmaData == null) {

                for (EmaData emaData : emaDataList) {
                    emaDataRepository.save(emaData);
                }
            } else {
                for (EmaData emaData : emaDataList) {
                    if (lastEmaData.getEmaDataKey().getDay().before(emaData.getEmaDataKey().getDay())) {
                        emaDataRepository.save(emaData);
                    }
                }
            }

            MacdData lastMacdData = macdDataRepository.getLatestRecord(ticker);
            List<MacdData> macdDataList = alphaVantageClient.getMacdData(ticker);
            if (lastMacdData == null) {

                for (MacdData macdData : macdDataList) {
                    macdDataRepository.save(macdData);
                }
            } else {
                for (MacdData macdData : macdDataList) {
                    if (lastMacdData.getMacdDataKey().getDay().before(lastMacdData.getMacdDataKey().getDay())) {
                        macdDataRepository.save(macdData);
                    }
                }
            }
        }
        return result.toString();
    }


}
