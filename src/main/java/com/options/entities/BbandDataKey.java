package com.options.entities;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
public class BbandDataKey implements Serializable {

    private LocalDate day;

    private String ticker;

    public BbandDataKey(){}

    public BbandDataKey(LocalDate day, String ticker){
        this.day = day;
        this.ticker = ticker;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
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
        return "BbandDataKey{" +
                "day=" + day +
                ", ticker='" + ticker + '\'' +
                '}';
    }
}