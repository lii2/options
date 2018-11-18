package com.options.entities;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Embeddable
public class StockDataKey implements Serializable {

    private Date day;

    private String ticker;

    public StockDataKey() {
    }

    public StockDataKey(Date day, String ticker) {
        this.day = day;
        this.ticker = ticker;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    @Override
    public String toString() {
        return "StockDataKey{" +
                "day=" + day +
                ", ticker='" + ticker + '\'' +
                '}';
    }
}
