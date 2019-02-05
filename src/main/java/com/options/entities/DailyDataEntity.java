package com.options.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class DailyDataEntity {

    @Id
    @Column(insertable = false, updatable = false)
    private int dailyDataKey;

    private String ticker;

    private LocalDate day;

    @OneToOne
    private TimeSeriesDailyEntity timeSeriesDaily;

    @OneToOne
    private DailyTechnicalsEntity dailyTechnicals;

    @Override
    public String toString() {
        return "DailyDataEntity{" +
                "dailyDataKey=" + dailyDataKey +
                ", ticker='" + ticker + '\'' +
                ", day=" + day +
                '}';
    }
}
