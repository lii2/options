package com.options.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class MacdEntity {

    @Id
    @Column(insertable = false, updatable = false)
    private int macdKey;

    private String seriesType;

    private int fastPeriod;

    private int slowPeriod;

    private int signalPeriod;

    private BigDecimal macdHist;

    private BigDecimal macdSignal;

    private BigDecimal macd;

    @Column(insertable = false, updatable = false)
    private int dailyTechnicalsKey;

    @OneToOne
    @JoinColumn(name = "dailyTechnicalsKey")
    private DailyTechnicalsEntity dailyTechnicals;

    @Override
    public String toString() {
        return "MacdEntity{" +
                "macdKey=" + macdKey +
                ", seriesType='" + seriesType + '\'' +
                ", fastPeriod=" + fastPeriod +
                ", slowPeriod=" + slowPeriod +
                ", signalPeriod=" + signalPeriod +
                ", macdHist=" + macdHist +
                ", macdSignal=" + macdSignal +
                ", macd=" + macd +
                ", dailyTechnicalsKey=" + dailyTechnicalsKey +
                '}';
    }
}
