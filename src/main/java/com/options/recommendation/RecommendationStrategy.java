package com.options.recommendation;

import com.options.entities.RecommendationStrategyEntity;
import com.options.entities.RecommendationStrategyVersionEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class RecommendationStrategy {

    private int recommendationStrategyKey;

    private String trend;

    private String name;

    private String description;

    private List<RecommendationStrategyVersion> versions;

    public RecommendationStrategy(RecommendationStrategyEntity entity) {
        this.recommendationStrategyKey = entity.getRecommendationStrategyKey();
        this.trend = entity.getTrend();
        this.name = entity.getName();
        this.description = entity.getDescription();
        List<RecommendationStrategyVersion> databaseVersions = new ArrayList<>();
        for (RecommendationStrategyVersionEntity version : entity.getVersions()) {
            databaseVersions.add(new RecommendationStrategyVersion(version));
        }
        this.versions = databaseVersions;
    }
}
