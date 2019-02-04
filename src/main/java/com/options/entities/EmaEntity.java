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
public class EmaEntity {

    @Id
    public int emaKey;

    public String seriesType;

    public int timePeriod;

    public BigDecimal ema;

    public int dailyTechnicalsKey;


    @Override
    public String toString() {
        return "EmaEntity{" +
                "emaKey=" + emaKey +
                ", seriesType='" + seriesType + '\'' +
                ", timePeriod=" + timePeriod +
                ", ema=" + ema +
                ", dailyTechnicalsKey=" + dailyTechnicalsKey +
                '}';
    }
}
