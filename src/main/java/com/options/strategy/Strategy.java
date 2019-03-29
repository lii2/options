package com.options.strategy;

import com.options.data.DailyData;
import com.options.recommendation.Recommendation;

import java.util.Optional;

abstract class Strategy  {

    // On this day, is there a signal for action on the next day?
    abstract Optional<Recommendation> decide(DailyData dailyData);

}
