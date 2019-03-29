package com.options.agents;

import com.options.recommendation.Recommendation;
import com.options.data.DailyData;
import com.options.recommendation.EntranceStrategies;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class Analyst {

    private int daysOfData;

    private List<DailyData> dailyDataList;

    @Autowired
    public Analyst() {
        this.daysOfData = 100;
    }

    public List<Recommendation> analyzeData() {
        List<Recommendation> pendingRecommendations = new ArrayList<>();
        // TODO: FIND A WAY TO INDICATE WHEN TO SELL IRON CONDORS
        int lastDayIndex = dailyDataList.size() - 1;
        EntranceStrategies entranceStrategies = new EntranceStrategies();
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
}
