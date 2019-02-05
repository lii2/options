package com.options.entities;

import com.options.domain.data.DailyData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
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
    private MacdEntity macd;

    @OneToOne
    private BbandsEntity bbands;

    @OneToOne
    private EmaEntity ema;
}
