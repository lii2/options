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
    @Column(insertable = false, updatable = false)
    private int timeSeriesDailyKey;

    private BigDecimal open;

    private BigDecimal close;

    private BigDecimal high;

    private BigDecimal low;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dailyDataKey")
    private DailyDataEntity dailyDataEntity;

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
