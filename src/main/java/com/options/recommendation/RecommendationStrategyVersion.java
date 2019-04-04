package com.options.recommendation;

import com.options.backtest.BacktestResult;
import com.options.entities.RecommendationStrategyVersionEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecommendationStrategyVersion {

    private Integer version;

    private BacktestResult backtestResult;

    public RecommendationStrategyVersion(RecommendationStrategyVersionEntity entity){
        this.version = entity.getVersion();
        // TODO Write backtest result constructor
    }
}
