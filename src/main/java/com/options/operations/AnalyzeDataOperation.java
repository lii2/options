package com.options.operations;

import com.options.domain.choice.Recommendation;
import com.options.domain.data.DailyData;
import com.options.domain.trend.Trend;
import com.options.entities.EmaData;
import com.options.entities.MacdData;
import com.options.entities.StockData;
import com.options.operations.analysis.EntranceStrategies;
import com.options.repositories.EmaDataRepository;
import com.options.repositories.MacdDataRepository;
import com.options.repositories.StockDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Component
public class AnalyzeDataOperation {

    @Autowired
    private StockDataRepository stockDataRepository;

    @Autowired
    private EmaDataRepository emaDataRepository;

    @Autowired
    private MacdDataRepository macdDataRepository;

    private int daysOfData;

    private List<DailyData> dailyDataList;

    public AnalyzeDataOperation() {
        this.daysOfData = 30;
    }

    public List<Recommendation> execute(String ticker) {
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
        EmaData[] lastXDaysEmaData = emaDataRepository.getLastXDays(ticker, daysOfData).stream().toArray(EmaData[]::new);
        StockData[] lastXDaysStockData = stockDataRepository.getLastXDays(ticker, daysOfData).stream().toArray(StockData[]::new);
        MacdData[] lastXDaysMacdData = macdDataRepository.getLastXDays(ticker, daysOfData).stream().toArray(MacdData[]::new);
        dailyDataList = DailyData.generateDailyData(lastXDaysStockData, lastXDaysEmaData, lastXDaysMacdData);
    }

    public void setDaysOfData(int daysOfData) {
        this.daysOfData = daysOfData;
    }
}
