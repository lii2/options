package com.options.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class StockData {

    @EmbeddedId
    private StockDataKey stockDataKey;

    private BigDecimal open;

    private BigDecimal high;

    private BigDecimal low;

    private BigDecimal close;

    private BigInteger volume;

    private transient static SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");

    public StockData() {
    }

    public StockData(String ticker, String[] data) throws ParseException {
        this.stockDataKey = new StockDataKey(smf.parse(data[0]), ticker);
        this.open = new BigDecimal(data[1]);
        this.high = new BigDecimal(data[2]);
        this.low = new BigDecimal(data[3]);
        this.close = new BigDecimal(data[4]);
        this.volume = new BigInteger(data[5].replace("\r",""));
    }

    public StockData(StockDataKey stockDataKey, BigDecimal open, BigDecimal high, BigDecimal low, BigDecimal close, BigInteger volume) {
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

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getClose() {
        return close;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }

    public BigInteger getVolume() {
        return volume;
    }

    public void setVolume(BigInteger volume) {
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
