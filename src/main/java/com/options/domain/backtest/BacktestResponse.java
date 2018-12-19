package com.options.domain.backtest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public BacktestResponse(List<RecommendationResult> results, BigDecimal totalGain,
                            BigDecimal totalLoss, BigDecimal biggestDrawDown,
                            int averageDaysHeldSuccess, int averageDaysHeldFailure,
                            int successes, int failures, int fizzles, int total, float percentSuccess,
                            float percentNotFailed) {
        this.results = results;
        this.totalGain = totalGain;
        this.totalLoss = totalLoss;
        this.biggestDrawDown = biggestDrawDown;
        this.averageDaysHeldSuccess = averageDaysHeldSuccess;
        this.averageDaysHeldFailure = averageDaysHeldFailure;
        this.successes = successes;
        this.failures = failures;
        this.fizzles = fizzles;
        this.total = total;
        this.percentSuccess = percentSuccess;
        this.percentNotFailed = percentNotFailed;
    }

    public List<RecommendationResult> getResults() {
        return results;
    }

    public void setResults(List<RecommendationResult> results) {
        this.results = results;
    }

    public BigDecimal getTotalGain() {
        return totalGain;
    }

    public void setTotalGain(BigDecimal totalGain) {
        this.totalGain = totalGain;
    }

    public BigDecimal getTotalLoss() {
        return totalLoss;
    }

    public void setTotalLoss(BigDecimal totalLoss) {
        this.totalLoss = totalLoss;
    }

    public BigDecimal getBiggestDrawDown() {
        return biggestDrawDown;
    }

    public void setBiggestDrawDown(BigDecimal biggestDrawDown) {
        this.biggestDrawDown = biggestDrawDown;
    }

    public int getAverageDaysHeldSuccess() {
        return averageDaysHeldSuccess;
    }

    public void setAverageDaysHeldSuccess(int averageDaysHeldSuccess) {
        this.averageDaysHeldSuccess = averageDaysHeldSuccess;
    }

    public int getAverageDaysHeldFailure() {
        return averageDaysHeldFailure;
    }

    public void setAverageDaysHeldFailure(int averageDaysHeldFailure) {
        this.averageDaysHeldFailure = averageDaysHeldFailure;
    }

    public int getSuccesses() {
        return successes;
    }

    public void setSuccesses(int successes) {
        this.successes = successes;
    }

    public int getFailures() {
        return failures;
    }

    public void setFailures(int failures) {
        this.failures = failures;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public float getPercentSuccess() {
        return percentSuccess;
    }

    public void setPercentSuccess(float percentSuccess) {
        this.percentSuccess = percentSuccess;
    }

    public int getFizzles() {
        return fizzles;
    }

    public void setFizzles(int fizzles) {
        this.fizzles = fizzles;
    }

    public float getPercentNotFailed() {
        return percentNotFailed;
    }

    public void setPercentNotFailed(float percentNotFailed) {
        this.percentNotFailed = percentNotFailed;
    }

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
