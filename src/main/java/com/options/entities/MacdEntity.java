package com.options.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "macd")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MacdEntity {

    @Id
    @Column(insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int macdKey;

    private BigDecimal macdHist;

    private BigDecimal macdSignal;

    private BigDecimal macd;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dailyDataKey")
    private DailyDataEntity dailyDataEntity;

    @Override
    public String toString() {
        return "MacdEntity{" +
                "macdKey=" + macdKey +
                ", macdHist=" + macdHist +
                ", macdSignal=" + macdSignal +
                ", macd=" + macd +
                '}';
    }
}
