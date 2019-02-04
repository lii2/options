package com.options.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class MacdEntity {

    @Id
    public int macdKey;

    public String seriesType;

    public int fastPeriod;

    public int slowPeriod;

    public int signalPeriod;

    public BigDecimal macdHist;

    public BigDecimal macdSignal;

    public BigDecimal macd;

    public int dailyTechnicalsKey;

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
