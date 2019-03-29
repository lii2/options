package com.options.strategy;

import com.options.data.DailyData;
import com.options.recommendation.Recommendation;

import java.util.Optional;

public class BullishEmaCrossingStrategy extends Strategy {

    public final int VERSION = 1;

    @Override
    Optional<Recommendation> decide(DailyData dailyData) {
        return Optional.empty();
    }

    private String generateRiseMessage(DailyData dailyData) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" price rose above the ema, price was: ").append(dailyData.getPreviousDaysData().getOpenCloseMean())
                .append(" ema is: ").append(dailyData.getEma() )
                .append(" price is now: ").append(dailyData.getOpenCloseMean() )
                .append(" Message: buy calls to sell.");
        return stringBuilder.toString();
    }

}
