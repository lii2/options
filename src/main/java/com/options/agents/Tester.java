package com.options.agents;

import com.options.json.responses.BacktestResponse;
import com.options.recommendation.RecommendationResult;
import com.options.recommendation.Recommendation;
import com.options.data.DailyData;
import com.options.clients.database.PostgreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class Tester {

    private final static int DAYS_HELD = 2;
    private List<Recommendation> recommendationList;
    private List<DailyData> dailyDataList;
    private int daysToTest;
    @Autowired
    private PostgreClient postgreClient;

    public Tester() {
        this.daysToTest = 100;
    }

    public BacktestResponse backtest(String ticker) {
        setDataFromDatabase(ticker);
        int lastDayIndex = dailyDataList.size() - 1;
        List<LocalDate> datesOfRecommendations = new ArrayList<>();
        for (Recommendation recommendation : recommendationList) {
            datesOfRecommendations.add(recommendation.getDataOfRecommendation().getDay());
        }
        List<RecommendationResult> results = new ArrayList<>();

        for (int i = lastDayIndex; i >= DAYS_HELD + 1; i--) {
            if (datesOfRecommendations.contains(dailyDataList.get(i).getDay())) {
                Recommendation recommendation = getRecommendationByDate(dailyDataList.get(i).getDay());
                RecommendationResult recommendationResult = new RecommendationResult(DAYS_HELD, recommendation.getTrend(),
                        dailyDataList.get(i).getOpenCloseMean().subtract(dailyDataList.get(i - DAYS_HELD).getOpenCloseMean()),
                        recommendation.getDataOfRecommendation().getDay());
                results.add(recommendationResult);
            }
        }

        int successes = 0, failures = 0, fizzles = 0;
        BigDecimal totalGain = new BigDecimal(0);
        BigDecimal totalLoss = new BigDecimal(0);
        BigDecimal biggestDrawDown = new BigDecimal(0);

        for (RecommendationResult result : results) {
            switch (result.getOutcome()) {
                case SUCCESS:
                    successes++;
                    totalGain = totalGain.add(result.getPriceChange().abs());
                    break;
                case FAILURE:
                    failures++;
                    totalLoss = totalLoss.add(result.getPriceChange().abs());
                    if (result.getPriceChange().abs().compareTo(biggestDrawDown) > 0) {
                        biggestDrawDown = result.getPriceChange().abs();
                    }
                    break;
                case INDIFFERENT:
                    fizzles++;
                    break;
            }

        }

        float percentSuccess = successes * 100f / recommendationList.size();
        float percentNotFailed = (successes + fizzles) * 100f / recommendationList.size();

        return new BacktestResponse(results, totalGain, totalLoss, biggestDrawDown, DAYS_HELD, DAYS_HELD,
                successes, failures, fizzles, recommendationList.size(), percentSuccess, percentNotFailed);
    }

    public void setRecommendationList(List<Recommendation> recommendationList) {
        this.recommendationList = recommendationList;
    }

    private void setDataFromDatabase(String ticker) {
        dailyDataList = DailyData.generateDailyData(postgreClient.getLast100DaysData(ticker));
    }

    private Recommendation getRecommendationByDate(LocalDate LocalDate) {
        for (Recommendation recommendation : recommendationList) {
            if (recommendation.getDataOfRecommendation().getDay().equals(LocalDate))
                return recommendation;
        }
        return null;
    }
}
