package com.options.recommendation;

import com.options.data.DailyData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recommendation {

    private Trend trend;
    private String message;
    private DailyData dataOfRecommendation;

    @Override
    public String toString() {
        return "\nRecommendation{" +
                "trend=" + trend +
                ", message='" + message + '\'' +
                ", dataOfRecommendation=" + dataOfRecommendation +
                '}';
    }
}
