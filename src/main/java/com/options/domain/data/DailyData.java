package com.options.domain.data;

import com.options.entities.EmaData;
import com.options.entities.MacdData;
import com.options.entities.StockData;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.options.domain.DomainConstants.BIG_DECIMAL_TWO;

public class DailyData {

    private Date day;

    private String ticker;

    private BigDecimal open;

    private BigDecimal high;

    private BigDecimal low;

    private BigDecimal close;

    private BigInteger volume;

    private BigDecimal ema;

    private BigDecimal openCloseMean;

    private BigDecimal macd;

    private DailyData previousDaysData;

    private DailyData nextDaysData;

    public DailyData() {
    }

    public DailyData(StockData stockData, EmaData emaData, MacdData macdData) {
        if (!emaData.getEmaDataKey().getDay().equals(stockData.getStockDataKey().getDay())
                || !emaData.getEmaDataKey().getTicker().equalsIgnoreCase(stockData.getStockDataKey().getTicker())) {
            throw new UnsyncedDataException("Trying to created a DailyData object with unsynced data");
        }
        this.day = emaData.getEmaDataKey().getDay();
        this.ticker = emaData.getEmaDataKey().getTicker();
        this.open = stockData.getOpen();
        this.high = stockData.getHigh();
        this.low = stockData.getLow();
        this.close = stockData.getClose();
        this.volume = stockData.getVolume();
        this.ema = emaData.getEma();
        this.macd = macdData.getMacd();
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

    public BigDecimal getOpenCloseMean() {
        if (openCloseMean == null) {
            openCloseMean = open.add(close).divide(BIG_DECIMAL_TWO, RoundingMode.DOWN);
        }
        return openCloseMean;
    }

    public boolean averagedBelowEma() {
        return (ema.compareTo(getOpenCloseMean()) > 0);
    }

    public static List<DailyData> generateDailyData(StockData[] stockData, EmaData[] emaData, MacdData[] macdData) {
        List<DailyData> dailyDataList = new ArrayList<>();
        for (int i = 0; i < stockData.length; i++) {
            DailyData dailyData = new DailyData(stockData[i], emaData[i], macdData[i]);
            dailyDataList.add(dailyData);
        }

        //  index Zero is the most recent data. By going from 0 to infinite we are going backwards.
        for (int i = 0; i < stockData.length - 1; i++) {
            dailyDataList.get(i).setPreviousDaysData(dailyDataList.get(i + 1));
        }

        for (int i = 1; i < stockData.length; i++) {
            dailyDataList.get(i).setNextDaysData(dailyDataList.get(i - 1));
        }

        return dailyDataList;
    }

    public DailyData getPreviousDaysData() {
        if (previousDaysData == null) {
            throw new UnsyncedDataException("Previous days data is null");
        }
        return previousDaysData;
    }

    public void setPreviousDaysData(DailyData previousDaysData) {
        if (previousDaysData.getDay().after(day)) {
            throw new UnsyncedDataException("Incorrectly setting previousDaysData in DailyData.java");
        }
        this.previousDaysData = previousDaysData;
    }

    public DailyData getNextDaysData() {
        if (nextDaysData == null) {
            throw new UnsyncedDataException("Next days data is null");
        }
        return nextDaysData;
    }

    public void setNextDaysData(DailyData nextDaysData) {
        if (day.after(nextDaysData.getDay())) {
            throw new UnsyncedDataException("Incorrectly setting nextDaysData in DailyData.java");
        }
        this.nextDaysData = nextDaysData;
    }


    public BigDecimal getMacd() {
        return macd;
    }

    public void setMacd(BigDecimal macd) {
        this.macd = macd;
    }

    @Override
    public String toString() {
        return "DailyData{" +
                "day=" + day +
                ", ticker='" + ticker + '\'' +
                ", openCloseMean=" + openCloseMean +
                '}';
    }
}
