package com.options.operations;

import com.options.domain.choice.Recommendation;
import com.options.domain.data.DailyData;
import com.options.entities.EmaData;
import com.options.entities.StockData;
import com.options.repositories.EmaDataRepository;
import com.options.repositories.StockDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BacktestOperation {

    private List<Recommendation> recommendationList;

    private List<DailyData> dailyDataList;

    private int daysOfData;

    @Autowired
    private StockDataRepository stockDataRepository;

    @Autowired
    private EmaDataRepository emaDataRepository;

    public BacktestOperation() {
        this.daysOfData = 100;
    }

    public String execute() {
        String result = "";
        List<Date> dates = dailyDataList.stream().map(e -> e.getDay()).collect(Collectors.toList());
        for (DailyData dailyData : dailyDataList) {
            if (dates.contains(dailyData.getDay())){
                // TODO fix this
            }

        }

        return result;
    }

    public void setRecommendationList(List<Recommendation> recommendationList) {
        this.recommendationList = recommendationList;
    }

    private void setDataFromDatabase() {
        EmaData[] last30DaysEmaData = emaDataRepository.getLastXDays(daysOfData).stream().toArray(EmaData[]::new);
        StockData[] last30DaysStockData = stockDataRepository.getLastXDays(daysOfData).stream().toArray(StockData[]::new);
        dailyDataList = DailyData.generateDailyData(last30DaysStockData, last30DaysEmaData);
    }

}
