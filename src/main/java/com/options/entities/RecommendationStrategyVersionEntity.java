package com.options.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "RECOMMENDATION_STRATEGY_VERSION")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class RecommendationStrategyVersionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recommendationStrategyVersionKey;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recommendationStrategyKey")
    private RecommendationStrategyEntity recommendationStrategy;

    private Integer version;

    @Override
    public String toString() {
        return "RecommendationStrategyVersionEntity{" +
                "recommendationStrategyVersionKey=" + recommendationStrategyVersionKey +
                ", recommendationStrategy=" + recommendationStrategy +
                ", version='" + version + '\'' +
                '}';
    }
}
