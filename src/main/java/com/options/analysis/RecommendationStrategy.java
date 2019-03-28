package com.options.analysis;

import com.options.entities.RecommendationStrategyEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecommendationStrategy {

    private int recommendationStrategyKey;

    private String trend;

    private String name;

    private String description;

    public RecommendationStrategy(RecommendationStrategyEntity entity) {
        this.recommendationStrategyKey = entity.getRecommendationStrategyKey();
        this.trend = entity.getTrend();
        this.name = entity.getName();
        this.description = entity.getDescription();
    }
}
