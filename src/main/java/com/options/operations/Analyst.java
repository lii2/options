package com.options.operations;

import com.options.analysis.Recommendation;
import com.options.data.DailyData;
import com.options.analysis.EntranceStrategies;
import com.options.clients.database.PostgreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Analyst {

    @Autowired
    private PostgreClient postgreClient;

    private int daysOfData;

    private List<DailyData> dailyDataList;

    @Autowired
    public Analyst() {
        this.daysOfData = 30;
    }

    public List<Recommendation> analyzeData(String ticker) {
        setDataFromDatabase(ticker);
        return doAnalysis();
    }

    private List<Recommendation> doAnalysis() {
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

    private void setDataFromDatabase(String ticker) {
        dailyDataList = DailyData.generateDailyData(postgreClient.getLast100DaysData(ticker));
    }

    public void setDaysOfData(int daysOfData) {
        this.daysOfData = daysOfData;
    }

    public List<Recommendation> analyzeData(int daysToAnalyze, String ticker) {
        setDaysOfData(daysToAnalyze);
        return analyzeData(ticker);
    }
}
