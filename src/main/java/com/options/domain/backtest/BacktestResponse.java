package com.options.domain.backtest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BacktestResponse {

    List<RecommendationResult> results = new ArrayList<>();
    private BigDecimal totalGain;
    private BigDecimal totalLoss;
    private BigDecimal biggestDrawDown;
    private int averageDaysHeldSuccess;
    private int averageDaysHeldFailure;
    private int successes;
    private int failures;
    private int fizzles;
    private int total;
    private float percentSuccess;
    private float percentNotFailed;

    @Override
    public String toString() {
        return "BacktestResponse{" +
                ", totalGain=" + totalGain +
                ", totalLoss=" + totalLoss +
                ", biggestDrawDown=" + biggestDrawDown +
                ", averageDaysHeldSuccess=" + averageDaysHeldSuccess +
                ", averageDaysHeldFailure=" + averageDaysHeldFailure +
                ", successes=" + successes +
                ", failures=" + failures +
                ", fizzles=" + fizzles +
                ", total=" + total +
                ", percentSuccess=" + percentSuccess +
                ", percentNotFailed=" + percentNotFailed +
                "\nresults=" + Arrays.toString(results.toArray()) +

                '}';
    }

}
