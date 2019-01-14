package com.options.domain.choice;

import com.options.domain.data.DailyData;
import com.options.domain.trend.Trend;
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
        return "\n \nRecommendation{" +
                "trend=" + trend +
                ", message='" + message + '\'' +
                ", dataOfRecommendation=" + dataOfRecommendation +
                '}';
    }
}
