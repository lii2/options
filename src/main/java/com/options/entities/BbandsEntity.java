package com.options.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "bbands")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BbandsEntity {

    @Id
    @Column(insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bbandsKey;

    private BigDecimal realMiddleBand;

    private BigDecimal realUpperBand;

    private BigDecimal realLowerBand;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dailyDataKey")
    private DailyDataEntity dailyDataEntity;

    @Override
    public String toString() {
        return "BbandsEntity{" +
                "bbandsKey=" + bbandsKey +
                ", realMiddleBand=" + realMiddleBand +
                ", realUpperBand=" + realUpperBand +
                ", realLowerBand=" + realLowerBand +
                '}';
    }
}
