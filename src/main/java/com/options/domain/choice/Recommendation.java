package com.options.domain.choice;

import com.options.domain.data.DailyData;
import com.options.domain.trend.Trend;

public class Recommendation {

    private Trend trend;

    private String message;

    private DailyData dataOfRecommendation;

    public Recommendation(Trend trend, String message, DailyData dailyData) {
        this.trend = trend;
        this.message = message;
        this.dataOfRecommendation = dailyData;
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

    public DailyData getDataOfRecommendation() {
        return dataOfRecommendation;
    }

    public void setDataOfRecommendation(DailyData dataOfRecommendation) {
        this.dataOfRecommendation = dataOfRecommendation;
    }

    @Override
    public String toString() {
        return "\n \nRecommendation{" +
                "trend=" + trend +
                ", message='" + message + '\'' +
                ", dataOfRecommendation=" + dataOfRecommendation +
                '}';
    }
}
