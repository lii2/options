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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tickerKey;

    private String tickerSymbol;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "tickerKey")
    private List<DailyDataEntity> dailyDataList;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "tickerKey")
    private List<BacktestResultEntity> backtestResultList;

    public TickerEntity(String tickerSymbol){
        this.tickerSymbol = tickerSymbol;
    }

    @Override
    public String toString() {
        return "TickerEntity{" +
                "tickerKey=" + tickerKey +
                ", tickerSymbol='" + tickerSymbol + '\'' +
                '}';
    }
}
