package com.options.agents;

import com.options.technicals.DailyTechnicals;
import com.options.recommendation.Recommendation;
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

    private List<DailyTechnicals> dailyTechnicalsList;

    @Autowired
    public Analyst() {
        this.daysOfData = 100;
    }

    public List<Recommendation> analyzeData() {
        List<Recommendation> pendingRecommendations = new ArrayList<>();
        // TODO: FIND A WAY TO INDICATE WHEN TO SELL IRON CONDORS
        int lastDayIndex = dailyTechnicalsList.size() - 1;
        EntranceStrategies entranceStrategies = new EntranceStrategies();
        // Main Loop
        for (int i = lastDayIndex - 1; i >= 0; i--) {
            entranceStrategies.findEntrance(dailyTechnicalsList.get(i), pendingRecommendations);
        }
        return pendingRecommendations;
    }

    public List<Recommendation> analyzeData(int daysToAnalyze) {
        this.daysOfData = daysToAnalyze;
        return analyzeData();
    }
}
