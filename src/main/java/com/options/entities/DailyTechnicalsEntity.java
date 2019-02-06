package com.options.entities;

import com.options.domain.data.DailyData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "daily_technicals")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class DailyTechnicalsEntity {

    @Id
    @Column(insertable = false, updatable = false)
    private int dailyTechnicalsKey;

    @Column(insertable = false, updatable = false)
    private int dailyDataKey;

    @OneToOne
    @JoinColumn(name = "dailyDataKey")
    private DailyDataEntity dailyData;

    @OneToOne
    @JoinColumn(name = "dailyTechnicalsKey")
    private MacdEntity macd;

    @OneToOne
    @JoinColumn(name = "dailyTechnicalsKey")
    private BbandsEntity bbands;

    @OneToOne
    @JoinColumn(name = "dailyTechnicalsKey")
    private EmaEntity ema;

    @Override
    public String toString() {
        return "DailyTechnicalsEntity{" +
                "dailyTechnicalsKey=" + dailyTechnicalsKey +
                ", macd=" + macd +
                ", bbands=" + bbands +
                ", ema=" + ema +
                '}';
    }
}
