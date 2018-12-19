package com.options.operations;

import com.options.domain.choice.Recommendation;
import com.options.domain.data.DailyData;
import com.options.entities.EmaData;
import com.options.entities.MacdData;
import com.options.entities.StockData;
import com.options.repositories.EmaDataRepository;
import com.options.repositories.MacdDataRepository;
import com.options.repositories.StockDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class BacktestOperation {

    private List<Recommendation> recommendationList;

    private List<DailyData> dailyDataList;

    private int daysOfData;

    @Autowired
    private StockDataRepository stockDataRepository;

    @Autowired
    private EmaDataRepository emaDataRepository;

    @Autowired
    private MacdDataRepository macdDataRepository;

    public BacktestOperation() {
        this.daysOfData = 100;
    }

    public String execute(String ticker) {
        setDataFromDatabase(ticker);
        StringBuilder result = new StringBuilder();
        int lastDayIndex = dailyDataList.size() - 1;
        int successes = 0;
        List<Date> datesOfRecommendations = new ArrayList<>();
        for (Recommendation recommendation : recommendationList) {
            datesOfRecommendations.add(recommendation.getDataOfRecommendation().getDay());
        }

        for (int i = lastDayIndex; i >= 3; i--) {
            if (datesOfRecommendations.contains(dailyDataList.get(i).getDay())) {
                switch (getRecommendationByDate(dailyDataList.get(i).getDay()).getTrend()) {
                    case BEARISH:
                        if (dailyDataList.get(i - 2).getOpenCloseMean().compareTo(dailyDataList.get(i).getOpenCloseMean()) < 0) {
                            result.append(dailyDataList.get(i).getDay()).append(": Bullish SUCCESS ")
                                    .append(dailyDataList.get(i - 2).getOpenCloseMean().subtract(dailyDataList.get(i).getOpenCloseMean()))
                                    .append("$\n");
                            successes++;
                        } else {
                            result.append(dailyDataList.get(i).getDay()).append(": Bearish FAILURE ")
                                    .append(dailyDataList.get(i - 2).getOpenCloseMean().subtract(dailyDataList.get(i).getOpenCloseMean()))
                                    .append("$\n");
                        }
                        break;
                    case BULLISH:
                        if (dailyDataList.get(i - 2).getOpenCloseMean().compareTo(dailyDataList.get(i).getOpenCloseMean()) > 0) {
                            result.append(dailyDataList.get(i).getDay()).append(": Bullish SUCCESS ")
                                    .append(dailyDataList.get(i - 2).getOpenCloseMean().subtract(dailyDataList.get(i).getOpenCloseMean()))
                                    .append("$\n");
                            successes++;
                        } else {
                            result.append(dailyDataList.get(i).getDay()).append(": Bearish FAILURE ")
                                    .append(dailyDataList.get(i - 2).getOpenCloseMean().subtract(dailyDataList.get(i).getOpenCloseMean()))
                                    .append("$\n");
                        }
                        break;
                }
            }
        }
        result.append("Succeeded ").append(successes).append("/").append(recommendationList.size()).append(" times: ")
                .append(successes * 100f / recommendationList.size()).append("%\n");
        return result.toString();
    }

    public void setRecommendationList(List<Recommendation> recommendationList) {
        this.recommendationList = recommendationList;
    }

    private void setDataFromDatabase(String ticker) {
        EmaData[] emaData = emaDataRepository.getLastXDays(ticker, daysOfData).stream().toArray(EmaData[]::new);
        StockData[] stockData = stockDataRepository.getLastXDays(ticker, daysOfData).stream().toArray(StockData[]::new);
        MacdData[] macdData = macdDataRepository.getLastXDays(ticker, daysOfData).stream().toArray(MacdData[]::new);
        dailyDataList = DailyData.generateDailyData(stockData, emaData, macdData);
    }

    private Recommendation getRecommendationByDate(Date date) {
        for (Recommendation recommendation : recommendationList) {
            if (recommendation.getDataOfRecommendation().getDay().equals(date))
                return recommendation;
        }
        return null;
    }
}
