package com.options.entities;

import com.google.common.base.Ticker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "daily_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class DailyDataEntity {

    @Id
    @Column(insertable = false, updatable = false)
    private int dailyDataKey;
    private LocalDate day;

    @OneToOne
    @JoinColumn(name = "dailyDataKey")
    private TimeSeriesDailyEntity timeSeriesDaily;

    @ManyToOne
    @JoinColumn(name = "tickerKey")
    private TickerEntity tickerEntity;

    @OneToOne
    @JoinColumn(name = "dailyDataKey")
    private MacdEntity macdEntity;

    @OneToOne
    @JoinColumn(name = "dailyDataKey")
    private BbandsEntity bbandsEntity;

    @OneToOne
    @JoinColumn(name = "dailyDataKey")
    private EmaEntity emaEntity;

    @Override
    public String toString() {
        return "DailyDataEntity{" +
                "dailyDataKey=" + dailyDataKey +
                ", day=" + day +
                ", timeSeriesDaily=" + timeSeriesDaily +
                ", macd=" + macdEntity +
                ", bbands=" + bbandsEntity +
                ", ema=" + emaEntity +
                ", tickerEntity=" + tickerEntity +
                '}';
    }
}
