package com.options.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

@Entity
public class StockData extends EntityData {

    @EmbeddedId
    private StockDataKey stockDataKey;

    private BigDecimal open;

    private BigDecimal high;

    private BigDecimal low;

    private BigDecimal close;

    private BigInteger volume;

    public StockData() {
    }

    public StockData(String ticker, String[] row) throws ParseException {
        this.stockDataKey = new StockDataKey(parseDate(row[0]), ticker);
        this.open = new BigDecimal(row[1]);
        this.high = new BigDecimal(row[2]);
        this.low = new BigDecimal(row[3]);
        this.close = new BigDecimal(row[4]);
        this.volume = new BigInteger(row[5].replace("\r", ""));
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

    public String getFormattedVolume() {
        return NumberFormat.getNumberInstance(Locale.US).format(volume);
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
