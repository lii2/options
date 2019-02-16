package com.options.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "time_series_daily")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class TimeSeriesDailyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int timeSeriesDailyKey;

    private BigDecimal open;

    private BigDecimal close;

    private BigDecimal high;

    private BigDecimal low;

    @Override
    public String toString() {
        return "TimeSeriesDailyEntity{" +
                "timeSeriesDailyKey=" + timeSeriesDailyKey +
                ", open=" + open +
                ", close=" + close +
                ", high=" + high +
                ", low=" + low +
                '}';
    }
}
