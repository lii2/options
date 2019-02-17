package com.options.operations;

import com.options.domain.alphavantage.AlphaVantageClient;
import com.options.operations.persist.DatabaseClient;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class SmartPersistOperation {

    @Autowired
    private DatabaseClient databaseClient;

    private AlphaVantageClient alphaVantageClient;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd");

    private LocalDate parseDate(String dateString) {
        if (dateString.length() > 10) {
            return LocalDate.parse(dateString.substring(0, 10), DATE_TIME_FORMATTER);
        } else
            return LocalDate.parse(dateString, DATE_TIME_FORMATTER);
    }

    public SmartPersistOperation() {
        alphaVantageClient = new AlphaVantageClient();
    }

    public String smartPersist(String tickerSymbol) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        StringBuilder result = new StringBuilder();
        // TODO: Check if data from today is already in database, if so, skip persistance
        if (StringUtils.isNotBlank(tickerSymbol)) {
            tickerSymbol = tickerSymbol.toUpperCase();
            String[] timeSeriesData = alphaVantageClient.getLast100DaysTimeSeriesData(tickerSymbol);
            String[] emaData = alphaVantageClient.getLast100DaysEmaData(tickerSymbol, "10");
            String[] macdData = alphaVantageClient.getMacdData(tickerSymbol);
            String[] bbandData = alphaVantageClient.getBbandData(tickerSymbol);

            int t = 1, e = 1, m = 1, b = 1;

            while (t < timeSeriesData.length) {
                System.out.println("loop    : " + t);
                String[] timeSeriesRow = timeSeriesData[t].split(",");
                LocalDate day = parseDate(timeSeriesRow[0]);
                if(!databaseClient.getDailyDataByDay(day).isPresent()) {
                    while (!emaData[e].contains(timeSeriesRow[0])) {
                        e++;
                    }
                    while (!macdData[m].contains(timeSeriesRow[0])) {
                        m++;
                    }
                    while (!bbandData[b].contains(timeSeriesRow[0])) {
                        b++;
                    }
                    String[] emaRow = emaData[e].split(",");
                    String[] macdRow = macdData[m].split(",");
                    String[] bbandRow = bbandData[b].split(",");

                    databaseClient.persistData(tickerSymbol, day, new BigDecimal(timeSeriesRow[1]), new BigDecimal(timeSeriesRow[2]),
                            new BigDecimal(timeSeriesRow[3]), new BigDecimal(timeSeriesRow[4]), new BigDecimal(emaRow[1]),
                            new BigDecimal(macdRow[1]), new BigDecimal(macdRow[2]), new BigDecimal(macdRow[3]),
                            new BigDecimal(bbandRow[1]), new BigDecimal(bbandRow[2]), new BigDecimal(bbandRow[3]));

                    result.append(timeSeriesRow[0]).append(" data persisted to database\n");
                }
                t++;
            }
        }
        return result.toString();
    }

}
