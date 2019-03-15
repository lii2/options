package com.options.entities;

import com.options.analysis.Trend;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "RECOMMENDATION_STRATEGY")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RecommendationStrategyEntity {

    @Id
    @Column(insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recommendationStrategyKey;

    private Trend trend;

    private String name;

    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "recommendationStrategyKey")
    private List<RecommendationStrategyVersionEntity> versions;

    @Override
    public String toString() {
        return "RecommendationStrategyEntity{" +
                "recommendationStrategyKey=" + recommendationStrategyKey +
                ", trend=" + trend +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", versions=" + versions +
                '}';
    }
}
