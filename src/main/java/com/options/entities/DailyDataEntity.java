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

    public DailyDataEntity(LocalDate day, TickerEntity tickerEntity){
        this.day = day;
        this.tickerEntity = tickerEntity;
    }

    @OneToOne
    @JoinColumn(name = "dailyDataKey")
    private TimeSeriesDailyEntity timeSeriesDaily;

    @OneToOne
    @JoinColumn(name = "dailyDataKey")
    private DailyTechnicalsEntity dailyTechnicals;

    @ManyToOne
    @JoinColumn(name = "tickerKey")
    private TickerEntity tickerEntity;

    @Override
    public String toString() {
        return "DailyDataEntity{" +
                "dailyDataKey=" + dailyDataKey +
                ", ticker='" + tickerEntity + '\'' +
                ", day=" + day +
                ", timeSeriesDaily=" + timeSeriesDaily +
                ", dailyTechnicals=" + dailyTechnicals +
                '}';
    }

}
