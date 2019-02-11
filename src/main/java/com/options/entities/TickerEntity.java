package com.options.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ticker")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class TickerEntity {

    @Id
    @Column(insertable = false, updatable = false)
    private int tickerKey;

    private String tickerSymbol;

    @OneToMany
    @JoinColumn(name = "tickerKey")
    private List<DailyDataEntity> dailyDataList;

    @Override
    public String toString() {
        return "TickerEntity{" +
                "tickerKey=" + tickerKey +
                ", tickerSymbol='" + tickerSymbol + '\'' +
                '}';
    }
}
