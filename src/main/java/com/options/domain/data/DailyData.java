package com.options.domain.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.options.entities.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.options.domain.trend.ImportantNumbers.BIG_DECIMAL_TWO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyData {

    private LocalDate day;
    private String ticker;
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;
    private BigDecimal ema;
    private BigDecimal openCloseMean;
    private BigDecimal macdHist;
    private BigDecimal realLowerBand;
    private BigDecimal realUpperBand;

    @JsonIgnore
    private DailyData previousDaysData;
    @JsonIgnore
    private DailyData nextDaysData;

    public DailyData(StockData stockData, EmaData emaData, MacdData macdData, BbandData bbandData) {
        if (!emaData.getEmaDataKey().getDay().equals(stockData.getStockDataKey().getDay())
                || !emaData.getEmaDataKey().getTicker().equalsIgnoreCase(stockData.getStockDataKey().getTicker())
                || !emaData.getEmaDataKey().getTicker().equalsIgnoreCase(macdData.getMacdDataKey().getTicker())
                || !emaData.getEmaDataKey().getTicker().equalsIgnoreCase(bbandData.getBbandDataKey().getTicker())
                || !emaData.getEmaDataKey().getDay().equals(macdData.getMacdDataKey().getDay())
                || !emaData.getEmaDataKey().getDay().equals(bbandData.getBbandDataKey().getDay())) {
            throw new UnsyncedDataException("Trying to created a DailyData object with unsynced data");
        }
        this.day = emaData.getEmaDataKey().getDay();
        this.ticker = emaData.getEmaDataKey().getTicker();
        this.open = stockData.getOpen();
        this.high = stockData.getHigh();
        this.low = stockData.getLow();
        this.close = stockData.getClose();
        this.ema = emaData.getEma();
        this.macdHist = macdData.getMacdHist();
        this.realLowerBand = bbandData.getRealLowerBand();
        this.realUpperBand = bbandData.getRealUpperBand();
    }

    public static List<DailyData> generateDailyData(StockData[] stockData, EmaData[] emaData, MacdData[] macdData, BbandData[] bbandData) {
        List<DailyData> dailyDataList = new ArrayList<>();
        for (int i = 0; i < stockData.length; i++) {
            DailyData dailyData = new DailyData(stockData[i], emaData[i], macdData[i], bbandData[i]);
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

    public static List<DailyData> generateDailyData(List<DailyDataEntity> dailyDataEntities) {
        List<DailyData> dailyDataList = new ArrayList<>();
        for (DailyDataEntity dailyDataEntity : dailyDataEntities) {
            DailyData dailyData = new DailyData();
            dailyData.setTicker(dailyDataEntity.getTickerEntity().getTickerSymbol());
            dailyData.setDay(dailyDataEntity.getDay());
            dailyData.setOpen(dailyDataEntity.getTimeSeriesDaily().getOpen());
            dailyData.setHigh(dailyDataEntity.getTimeSeriesDaily().getHigh());
            dailyData.setLow(dailyDataEntity.getTimeSeriesDaily().getLow());
            dailyData.setClose(dailyDataEntity.getTimeSeriesDaily().getClose());
            dailyData.setEma(dailyDataEntity.getEmaEntity().getEma());
            dailyData.setMacdHist(dailyDataEntity.getMacdEntity().getMacdHist());
            dailyData.setRealLowerBand(dailyDataEntity.getBbandsEntity().getRealLowerBand());
            dailyData.setRealUpperBand(dailyDataEntity.getBbandsEntity().getRealUpperBand());
            dailyDataList.add(dailyData);
        }

        //  index Zero is the most recent data. By going from 0 to infinite we are going backwards.
        for (int i = 0; i < dailyDataList.size() - 1; i++) {
            dailyDataList.get(i).setPreviousDaysData(dailyDataList.get(i + 1));
        }

        for (int i = 1; i < dailyDataList.size(); i++) {
            dailyDataList.get(i).setNextDaysData(dailyDataList.get(i - 1));
        }
        return dailyDataList;
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

    public DailyData getPreviousDaysData() {
        if (previousDaysData == null) {
            throw new UnsyncedDataException("Previous days data is null");
        }
        return previousDaysData;
    }

    public void setPreviousDaysData(DailyData previousDaysData) {
        if (previousDaysData.getDay().isAfter(day)) {
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
        if (day.isAfter(nextDaysData.getDay())) {
            throw new UnsyncedDataException("Incorrectly setting nextDaysData in DailyData.java");
        }
        this.nextDaysData = nextDaysData;
    }

    public BigDecimal getBoxHigh() {
        if (open.compareTo(close) > 0)
            return open;
        else
            return close;
    }

    public BigDecimal getBoxLow() {
        if (open.compareTo(close) > 0)
            return close;
        else
            return open;
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
