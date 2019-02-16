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
@EqualsAndHashCode(callSuper=false)
public class BbandsEntity {

    @Id
    @Column(insertable = false, updatable = false)
    public int bbandsKey;

    public BigDecimal realMiddleBand;

    public BigDecimal realUpperBand;

    public BigDecimal realLowerBand;

    @OneToOne
    @JoinColumn(name = "dailyDataKey")
    private DailyDataEntity dailyDataKey;

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
