package com.options.domain.data;

import com.options.entities.EmaData;
import com.options.entities.StockData;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DailyData {

    private Date day;

    private String ticker;

    private BigDecimal open;

    private BigDecimal high;

    private BigDecimal low;

    private BigDecimal close;

    private BigInteger volume;

    private BigDecimal ema;

    public DailyData() {
    }

    public DailyData(StockData stockData, EmaData emaData) {
        if (!emaData.getEmaDataKey().getDay().equals(stockData.getStockDataKey().getDay())
                || !emaData.getEmaDataKey().getTicker().equalsIgnoreCase(stockData.getStockDataKey().getTicker())) {
            throw new UnsyncedDataException("Trying to created DailyData object with unsynced data");
        }
        this.day = emaData.getEmaDataKey().getDay();
        this.ticker = emaData.getEmaDataKey().getTicker();
        this.open = stockData.getOpen();
        this.high = stockData.getHigh();
        this.low = stockData.getLow();
        this.close = stockData.getClose();
        this.volume = stockData.getVolume();
        this.ema = emaData.getEma();
    }

    public DailyData(Date day, String ticker, BigDecimal open, BigDecimal high,
                     BigDecimal low, BigDecimal close, BigInteger volume, BigDecimal ema) {
        this.day = day;
        this.ticker = ticker;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.ema = ema;
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

    public BigDecimal getEma() {
        return ema;
    }

    public void setEma(BigDecimal ema) {
        this.ema = ema;
    }

    public BigDecimal openCloseMean() {
        return open.add(close).divide(BigDecimal.valueOf(2.0), RoundingMode.DOWN);
    }

    public boolean averagedBelowEma() {
        return (ema.compareTo(openCloseMean()) > 0);
    }

    public static List<DailyData> generateDailyData(StockData[] stockData, EmaData[] emaData) {
        List<DailyData> dailyDataList = new ArrayList<>();
        for (int i = 0; i < stockData.length; i++) {
            DailyData dailyData = new DailyData(stockData[i], emaData[i]);
            dailyDataList.add(dailyData);
        }
        return dailyDataList;
    }

    @Override
    public String toString() {
        return "DailyData{" +
                "day=" + day +
                ", ticker='" + ticker + '\'' +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", close=" + close +
                ", volume=" + volume +
                ", ema=" + ema +
                '}';
    }
}
