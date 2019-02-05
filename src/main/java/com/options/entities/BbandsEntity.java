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
public class BbandsEntity {

    @Id
    @Column(insertable = false, updatable = false)
    public int bBandsKey;

    public int timePeriod;

    public String seriesType;

    public int nbdevup;

    public int nbdevdn;

    public String maType;

    public BigDecimal realMiddleBand;

    public BigDecimal realUpperBand;

    public BigDecimal realLowerBand;

    @Column(insertable = false, updatable = false)
    public int dailyTechnicalsKey;

    @OneToOne
    @JoinColumn(name = "dailyTechnicalsKey")
    private DailyTechnicalsEntity dailyTechnicals;

    @Override
    public String toString() {
        return "BbandsEntity{" +
                "bBandsKey=" + bBandsKey +
                ", timePeriod=" + timePeriod +
                ", seriesType='" + seriesType + '\'' +
                ", nbdevup=" + nbdevup +
                ", nbdevdn=" + nbdevdn +
                ", maType='" + maType + '\'' +
                ", realMiddleBand=" + realMiddleBand +
                ", realUpperBand=" + realUpperBand +
                ", realLowerBand=" + realLowerBand +
                ", dailyTechnicalsKey=" + dailyTechnicalsKey +
                '}';
    }
}
