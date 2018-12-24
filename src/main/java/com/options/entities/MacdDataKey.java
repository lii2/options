package com.options.entities;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

@Embeddable
public class MacdDataKey implements Serializable {

    private transient static SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
    private LocalDate day;
    private String ticker;

    public MacdDataKey() {
    }

    public MacdDataKey(LocalDate day, String ticker) {
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
        return "MacdDataKey{" +
                "day=" + day +
                ", ticker='" + ticker + '\'' +
                '}';
    }
}
