package com.options.operations;

import com.options.domain.backtest.BacktestResponse;
import com.options.domain.backtest.RecommendationResult;
import com.options.domain.choice.Recommendation;
import com.options.domain.data.DailyData;
import com.options.entities.BbandData;
import com.options.entities.EmaData;
import com.options.entities.MacdData;
import com.options.entities.StockData;
import com.options.repositories.BbandDataRepository;
import com.options.repositories.EmaDataRepository;
import com.options.repositories.MacdDataRepository;
import com.options.repositories.StockDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class BacktestOperation {

    private final static int DAYS_HELD = 2;
    private List<Recommendation> recommendationList;
    private List<DailyData> dailyDataList;
    private int daysToTest;
    @Autowired
    private StockDataRepository stockDataRepository;

    @Autowired
    private EmaDataRepository emaDataRepository;

    @Autowired
    private MacdDataRepository macdDataRepository;

    @Autowired
    private BbandDataRepository bbandDataRepository;

    public BacktestOperation() {
        this.daysToTest = 100;
    }

    public BacktestResponse execute(String ticker) {
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
        EmaData[] emaData = emaDataRepository.getLastXDays(ticker, daysToTest).stream().toArray(EmaData[]::new);
        StockData[] stockData = stockDataRepository.getLastXDays(ticker, daysToTest).stream().toArray(StockData[]::new);
        MacdData[] macdData = macdDataRepository.getLastXDays(ticker, daysToTest).stream().toArray(MacdData[]::new);
        BbandData[] lastXDaysBbandData = bbandDataRepository.getLastXDays(ticker, daysToTest).stream().toArray(BbandData[]::new);
        dailyDataList = DailyData.generateDailyData(stockData, emaData, macdData, lastXDaysBbandData);
    }

    private Recommendation getRecommendationByDate(LocalDate LocalDate) {
        for (Recommendation recommendation : recommendationList) {
            if (recommendation.getDataOfRecommendation().getDay().equals(LocalDate))
                return recommendation;
        }
        return null;
    }
}
