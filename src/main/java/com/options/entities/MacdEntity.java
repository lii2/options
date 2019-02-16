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
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int macdKey;

    private BigDecimal macdHist;

    private BigDecimal macdSignal;

    private BigDecimal macd;

    public MacdEntity(BigDecimal macdHist, BigDecimal macdSignal, BigDecimal macd) {
        this.macdKey = 0;
        this.macdHist = macdHist;
        this.macdSignal = macdSignal;
        this.macd = macd;
    }

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
