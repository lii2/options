package com.options.backtest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BacktestResult {

    private int recommendationStrategyVersionKey;

    private String tickerSymbol;

    private LocalDate day;

    private BigDecimal biggestLoss;

    private int successes;

    private int failures;

    private int fizzles;

    private int total;

    private BigDecimal percentSuccess;

    private BigDecimal percentNotFailed;
}
