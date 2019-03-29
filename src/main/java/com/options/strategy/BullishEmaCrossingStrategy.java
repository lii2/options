package com.options.strategy;

import com.options.data.DailyData;
import com.options.recommendation.Recommendation;

import java.util.Optional;

public class BullishEmaCrossingStrategy extends Strategy {

    @Override
    Optional<Recommendation> decide(DailyData dailyData) {
        return Optional.empty();
    }

}
