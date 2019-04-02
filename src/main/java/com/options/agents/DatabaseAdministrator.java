package com.options.agents;

import com.options.recommendation.RecommendationStrategy;
import com.options.clients.alphavantage.AlphaVantageDataPackage;
import com.options.clients.database.PostgreClient;
import com.options.data.DailyData;
import com.options.entities.RecommendationStrategyEntity;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseAdministrator {

    @Autowired
    private PostgreClient postgreClient;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd");

    private static final String noNewDataFetched = "No new data added";

    private LocalDate parseDate(String dateString) {
        if (dateString.length() > 10) {
            return LocalDate.parse(dateString.substring(0, 10), DATE_TIME_FORMATTER);
        } else
            return LocalDate.parse(dateString, DATE_TIME_FORMATTER);
    }

    public String smartPersist(AlphaVantageDataPackage dataPackage, String tickerSymbol) {
        StringBuilder result = new StringBuilder();
        // TODO: Check if data from today is already in database, if so, skip persistance
        if (StringUtils.isNotBlank(tickerSymbol)) {
            tickerSymbol = tickerSymbol.toUpperCase();

            int timeSeriesIndex = 1, emaDataIndex = 1, macdDataIndex = 1, bbandDataIndex = 1;

            while (timeSeriesIndex < dataPackage.getTimeSeriesData().length) {
                String[] timeSeriesRow = dataPackage.getTimeSeriesData()[timeSeriesIndex].split(",");
                LocalDate day = parseDate(timeSeriesRow[0]);
                if (!postgreClient.getDailyDataByDayAndTicker(day, tickerSymbol).isPresent()) {
                    String[] emaRow = dataPackage.getEmaData()[emaDataIndex].split(",");
                    String[] macdRow = dataPackage.getMacdData()[macdDataIndex].split(",");
                    String[] bbandRow = dataPackage.getBbandData()[bbandDataIndex].split(",");
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
                    result.append(timeSeriesRow[0]).append(" data persisted to database \n ");
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

    public List<RecommendationStrategy> getRecommendationStrategies() {
        List<RecommendationStrategy> recommendationStrategies = new ArrayList<>();
        for (RecommendationStrategyEntity entity : postgreClient.getAllRecommendationStrategies()) {
            recommendationStrategies.add(new RecommendationStrategy(entity));
        }
        return recommendationStrategies;
    }

    public List<DailyData> getDailyData(String ticker){
        return DailyData.generateDailyData(postgreClient.getLast100DaysData(ticker));
    }
}
