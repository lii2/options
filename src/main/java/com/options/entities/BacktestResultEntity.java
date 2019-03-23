package com.options.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "BACKTEST_RESULT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class BacktestResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int backtestResultKey;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recommendationStrategyVersionKey")
    private RecommendationStrategyVersionEntity recommendationStrategyVersionKey;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tickerKey")
    private TickerEntity tickerKey;

    private LocalDate dateTested;

    private int daysToHold;

    private BigDecimal biggestLoss;

    private int successes;

    private int failures;

    private int fizzles;

    private int total;

    private BigDecimal percentSuccess;

    private BigDecimal percentNotFailed;

    @Override
    public String toString() {
        return "BacktestResultEntity{" +
                "backtestResultKey=" + backtestResultKey +
                ", recommendationStrategyVersionKey=" + recommendationStrategyVersionKey +
                ", tickerKey=" + tickerKey +
                ", dateTested=" + dateTested +
                ", daysToHold=" + daysToHold +
                ", biggestLoss=" + biggestLoss +
                ", successes=" + successes +
                ", failures=" + failures +
                ", fizzles=" + fizzles +
                ", total=" + total +
                ", percentSuccess=" + percentSuccess +
                ", percentNotFailed=" + percentNotFailed +
                '}';
    }
}
