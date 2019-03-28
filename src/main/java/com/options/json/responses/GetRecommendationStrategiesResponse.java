package com.options.json.responses;

import com.options.analysis.RecommendationStrategy;
import com.options.entities.RecommendationStrategyEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRecommendationStrategiesResponse {

    List<RecommendationStrategy> recommendationStrategies;

    @Override
    public String toString() {
        return "GetRecommendationStrategiesResponse{" +
                "recommendationStrategies=" + Arrays.toString(recommendationStrategies.toArray()) +
                '}';
    }
}
