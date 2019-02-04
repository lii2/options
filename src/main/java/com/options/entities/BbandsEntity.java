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
public class BbandsEntity {

    @Id
    public int bBandsKey;

    public int timePeriod;

    public String seriesType;

    public int nbdevup;

    public int nbdevdn;

    public String maType;

    public BigDecimal realMiddleBand;

    public BigDecimal realUpperBand;

    public BigDecimal realLowerBand;

    public int dailyTechnicalsKey;

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
