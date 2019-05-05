package com.options.recommendation;

import com.options.technicals.DailyTechnicals;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recommendation {

    private Trend trend;
    private String message;
    private DailyTechnicals dataOfRecommendation;

    @Override
    public String toString() {
        return "\nRecommendation{" +
                "trend=" + trend +
                ", message='" + message + '\'' +
                ", dataOfRecommendation=" + dataOfRecommendation +
                '}';
    }
}
