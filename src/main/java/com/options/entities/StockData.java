package com.options.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class StockData {

    @EmbeddedId
    private StockDataKey stockDataKey;

    private double open;

    private double high;

    private double low;

    private double close;

    private double volume;

    private transient static SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");

    public StockData() {
    }

    public StockData(String ticker, String[] data) throws ParseException {
        this.stockDataKey = new StockDataKey(smf.parse(data[0]), ticker);
        this.open = Double.valueOf(data[1]);
        this.high = Double.valueOf(data[2]);
        this.low = Double.valueOf(data[3]);
        this.close = Double.valueOf(data[4]);
        this.volume = Double.valueOf(data[5]);
    }

    public StockData(StockDataKey stockDataKey, double open, double high, double low, double close, double volume) {
        this.stockDataKey = stockDataKey;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }

    public StockDataKey getStockDataKey() {
        return stockDataKey;
    }

    public void setStockDataKey(StockDataKey stockDataKey) {
        this.stockDataKey = stockDataKey;
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
                "stockDataKey=" + stockDataKey +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", close=" + close +
                ", volume=" + volume +
                '}';
    }
}
