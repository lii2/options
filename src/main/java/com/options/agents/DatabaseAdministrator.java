package com.options.agents;

import com.options.clients.alphavantage.AlphaVantageClient;
import com.options.clients.database.PostgreClient;
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
public class DatabaseAdministrator {

    @Autowired
    private PostgreClient postgreClient;

    private AlphaVantageClient alphaVantageClient;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd");

    private static final String noNewDataFetched = "No new data added";

    private LocalDate parseDate(String dateString) {
        if (dateString.length() > 10) {
            return LocalDate.parse(dateString.substring(0, 10), DATE_TIME_FORMATTER);
        } else
            return LocalDate.parse(dateString, DATE_TIME_FORMATTER);
    }

    public DatabaseAdministrator() {
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

            int timeSeriesIndex = 1, emaDataIndex = 1, macdDataIndex = 1, bbandDataIndex = 1;

            while (timeSeriesIndex < timeSeriesData.length) {
                String[] timeSeriesRow = timeSeriesData[timeSeriesIndex].split(",");
                LocalDate day = parseDate(timeSeriesRow[0]);
                if (!postgreClient.getDailyDataByDay(day).isPresent()) {
                    String[] emaRow = emaData[emaDataIndex].split(",");
                    String[] macdRow = macdData[macdDataIndex].split(",");
                    String[] bbandRow = bbandData[bbandDataIndex].split(",");
                    LocalDate emaDate = parseDate(emaRow[0]);
                    LocalDate macdDate = parseDate(macdRow[0]);
                    LocalDate bbandDate = parseDate(bbandRow[0]);

                    if (day.equals(emaDate)
                            && day.equals(macdDate)
                            && day.equals(bbandDate)) {
                        postgreClient.persistData(tickerSymbol, day, new BigDecimal(timeSeriesRow[1]), new BigDecimal(timeSeriesRow[2]),
                                new BigDecimal(timeSeriesRow[3]), new BigDecimal(timeSeriesRow[4]), new BigDecimal(emaRow[1]),
                                new BigDecimal(macdRow[1]), new BigDecimal(macdRow[2]), new BigDecimal(macdRow[3]),
                                new BigDecimal(bbandRow[1]), new BigDecimal(bbandRow[2]), new BigDecimal(bbandRow[3]));
                    }
                    result.append(timeSeriesRow[0]).append(" data persisted to database\n");
                }
                timeSeriesIndex++;
                emaDataIndex++;
                macdDataIndex++;
                bbandDataIndex++;
            }
        }
        return result.length() < 1
                ? noNewDataFetched
                : result.toString();
    }
}
