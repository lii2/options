package com.options.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class StockData {

    @Id
    private Date day;

    private String ticker;

    private double open;

    private double high;

    private double low;

    private double close;

    private double volume;
    
    private transient static SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
    
    public StockData(){}

    public StockData(String ticker, String[] data) throws ParseException {
        this.ticker = ticker;
        this.day = smf.parse(data[0]);
        this.open = Double.valueOf(data[1]);
        this.high = Double.valueOf(data[2]);
        this.low = Double.valueOf(data[3]);
        this.close = Double.valueOf(data[4]);
        this.volume = Double.valueOf(data[5]);
    }
    
    public StockData(Date day, String ticker, double open, double high, double low, double close, double volume) {
        this.day = day;
        this.ticker = ticker;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
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

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "StockData{" +
                "day=" + day +
                ", ticker='" + ticker + '\'' +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", close=" + close +
                ", volume=" + volume +
                '}';
    }
}
