package com.options.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDataKey implements Serializable {

    private LocalDate day;
    private String ticker;

    @Override
    public String toString() {
        return "StockDataKey{" +
                "day=" + day +
                ", ticker='" + ticker + '\'' +
                '}';
    }
}
