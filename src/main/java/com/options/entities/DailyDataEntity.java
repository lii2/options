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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dailyDataKey;

    private LocalDate day;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dailyDataKey")
    private TimeSeriesDailyEntity timeSeriesDaily;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tickerKey")
    private TickerEntity tickerEntity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dailyDataKey")
    private MacdEntity macdEntity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dailyDataKey")
    private BbandsEntity bbandsEntity;

    @OneToOne(cascade = CascadeType.ALL)
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
