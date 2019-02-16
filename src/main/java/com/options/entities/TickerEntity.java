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
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int tickerKey;

    private String tickerSymbol;

    @OneToMany
    @JoinColumn(name = "tickerKey")
    private List<DailyDataEntity> dailyDataList;

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
