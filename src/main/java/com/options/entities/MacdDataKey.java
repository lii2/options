package com.options.entities;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Embeddable
public class MacdDataKey  implements Serializable {

    private Date day;

    private String ticker;

    private transient static SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");

    public MacdDataKey(){}

    public MacdDataKey(Date day, String ticker) {
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
        return "MacdDataKey{" +
                "day=" + day +
                ", ticker='" + ticker + '\'' +
                '}';
    }
}
