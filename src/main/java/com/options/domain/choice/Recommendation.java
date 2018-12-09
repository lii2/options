package com.options.domain.choice;

import com.options.domain.data.DailyData;
import com.options.domain.trend.Trend;

public class Recommendation {

    private Trend trend;

    private String message;

    private DailyData dayOfRecommendation;

    public Recommendation(Trend trend, String message, DailyData dailyData) {
        this.trend = trend;
        this.message = message;
        this.dayOfRecommendation = dailyData;
    }

    public Trend getTrend() {
        return trend;
    }

    public void setTrend(Trend trend) {
        this.trend = trend;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DailyData getDayOfRecommendation() {
        return dayOfRecommendation;
    }

    public void setDayOfRecommendation(DailyData dayOfRecommendation) {
        this.dayOfRecommendation = dayOfRecommendation;
    }

    @Override
    public String toString() {
        return "\n \nRecommendation{" +
                "trend=" + trend +
                ", message='" + message + '\'' +
                ", dayOfRecommendation=" + dayOfRecommendation +
                '}';
    }
}
