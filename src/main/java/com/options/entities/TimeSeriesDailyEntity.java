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
public class TimeSeriesDailyEntity {

    @Id
    public int timeSeriesDailyKey;

    public int dailyDataKey;

    public BigDecimal open;

    public BigDecimal close;

    public BigDecimal high;

    public BigDecimal low;


    @Override
    public String toString() {
        return "TimeSeriesDailyEntity{" +
                "timeSeriesDailyKey=" + timeSeriesDailyKey +
                ", dailyDataKey=" + dailyDataKey +
                ", open=" + open +
                ", close=" + close +
                ", high=" + high +
                ", low=" + low +
                '}';
    }
}
