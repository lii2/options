package com.options.agents;

import com.options.data.DailyData;
import com.options.recommendation.CurrentMarketTrends;
import com.options.recommendation.Recommendation;
import com.options.recommendation.Trend;
import com.options.strategy.EntranceStrategies;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class Analyst {

    private int daysOfData;

    // Set using Lombok setter method
    private List<DailyData> dailyDataList;

    private CurrentMarketTrends currentMarketTrends;

    @Autowired
    public Analyst() {
        this.daysOfData = 100;
    }

    public List<Recommendation> analyzeData() {
        List<Recommendation> pendingRecommendations = new ArrayList<>();
        determineCurrentMarketTrends();
        // TODO: FIND A WAY TO INDICATE WHEN TO SELL IRON CONDORS
        int lastDayIndex = daysOfData - 1;

        EntranceStrategies entranceStrategies = new EntranceStrategies(currentMarketTrends);

        // Main Loop
        for (int i = lastDayIndex - 1; i >= 0; i--) {
            entranceStrategies.findEntrance(dailyDataList.get(i), pendingRecommendations);
        }
        return pendingRecommendations;
    }

    public List<Recommendation> analyzeData(int daysToAnalyze) {
        this.daysOfData = daysToAnalyze;
        return analyzeData();
    }

    private void determineCurrentMarketTrends() {
        currentMarketTrends = new CurrentMarketTrends(Trend.getTrendUsingDifference(dailyDataList.get(0).getOpenCloseMean().subtract(dailyDataList.get(99).getOpenCloseMean())),
                Trend.getTrendUsingDifference(dailyDataList.get(0).getOpenCloseMean().subtract(dailyDataList.get(49).getOpenCloseMean())),
                Trend.getTrendUsingDifference(dailyDataList.get(0).getOpenCloseMean().subtract(dailyDataList.get(9).getOpenCloseMean())));

    }
}
