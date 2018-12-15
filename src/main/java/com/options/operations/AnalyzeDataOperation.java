package com.options.operations;

import com.options.domain.choice.Recommendation;
import com.options.domain.data.DailyData;
import com.options.domain.trend.Trend;
import com.options.domain.trend.Swing;
import com.options.entities.EmaData;
import com.options.entities.StockData;
import com.options.repositories.EmaDataRepository;
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
        List<Recommendation> recommendations = new ArrayList<>();
        // TODO: FIND A WAY TO INDICATE WHEN TO SELL IRON CONDORS
        int lastDayIndex = dailyDataList.size() - 1;
        // Main Loop
        for (int i = lastDayIndex - 1; i >= 0; i--) {
                 if (priceCrossedOverEma(dailyDataList.get(i))) {
                    if (dailyDataList.get(i).averagedBelowEma()) {
                        Recommendation recommendation = new Recommendation(Trend.BEARISH, generateDropMessage(dailyDataList.get(i)),
                                dailyDataList.get(i));
                        recommendations.add(recommendation);
                    } else {
                        Recommendation recommendation = new Recommendation(Trend.BULLISH, generateRiseMessage(dailyDataList.get(i)),
                                dailyDataList.get(i));
                        recommendations.add(recommendation);
                    }

                }
        }
        return recommendations;
    }

    private void setDataFromDatabase(String ticker) {
        EmaData[] last30DaysEmaData = emaDataRepository.getLastXDays(ticker, daysOfData).stream().toArray(EmaData[]::new);
        StockData[] last30DaysStockData = stockDataRepository.getLastXDays(ticker, daysOfData).stream().toArray(StockData[]::new);
        dailyDataList = DailyData.generateDailyData(last30DaysStockData, last30DaysEmaData);
    }

    private String generateRiseMessage(DailyData dailyData) {
        StringBuilder string = new StringBuilder();
        string.append("\nprice rose above the ema, price was: ").append(dailyData.getPreviousDaysData().getOpenCloseMean())
                .append("\nema is: ").append(dailyData.getEma())
                .append("\nprice is now: ").append(dailyData.getOpenCloseMean())
                .append("\nMessage: buy calls to sell.");
        return string.toString();
    }

    private String generateDropMessage(DailyData dailyData) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nprice dropped below the ema, price was: ").append(dailyData.getPreviousDaysData().getOpenCloseMean())
                .append("\nema is: ").append(dailyData.getEma())
                .append("\nprice is now: ").append(dailyData.getOpenCloseMean())
                .append("\nMessage: buy puts to sell.");
        return stringBuilder.toString();
    }

    public void setDaysOfData(int daysOfData) {
        this.daysOfData = daysOfData;
    }

    private BigDecimal calculateEmaVar(DailyData currentDay, BigDecimal previousEmaVar) {
        BigDecimal delta = currentDay.getOpenCloseMean().subtract(currentDay.getPreviousDaysData().getEma());
        BigDecimal alpha = currentDay.getEma().subtract(currentDay.getPreviousDaysData().getEma()).divide(delta, RoundingMode.DOWN);
        return (BigDecimal.ONE.subtract(alpha).multiply(previousEmaVar.add(alpha.multiply(delta.multiply(delta)))));
    }

    private boolean priceCrossedOverEma(DailyData dailyData) {
        return dailyData.averagedBelowEma() != dailyData.getPreviousDaysData().averagedBelowEma();
    }
}
