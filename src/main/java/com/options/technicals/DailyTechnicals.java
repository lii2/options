package com.options.technicals;

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

import static com.options.technicals.ImportantNumbers.BIG_DECIMAL_TWO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyTechnicals {

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
    private DailyTechnicals previousDaysData;
    @JsonIgnore
    private DailyTechnicals nextDaysData;

    public static List<DailyTechnicals> generateDailyData(List<DailyDataEntity> dailyDataEntities) {
        List<DailyTechnicals> dailyTechnicalsList = new ArrayList<>();


        for (DailyDataEntity dailyDataEntity : dailyDataEntities) {
            // For some reason, Hibernate makes some child entities null, only at the end.
            // TODO: FIGURE OUT WHY AND FIX.
            if (dailyDataEntity.getTimeSeriesDaily() != null
                    && dailyDataEntity.getMacdEntity() != null
                    && dailyDataEntity.getBbandsEntity() != null
                    && dailyDataEntity.getEmaEntity() != null) {
                DailyTechnicals dailyTechnicals = new DailyTechnicals();
                dailyTechnicals.setTicker(dailyDataEntity.getTickerEntity().getTickerSymbol());
                dailyTechnicals.setDay(dailyDataEntity.getDay());
                dailyTechnicals.setOpen(dailyDataEntity.getTimeSeriesDaily().getOpen());
                dailyTechnicals.setHigh(dailyDataEntity.getTimeSeriesDaily().getHigh());
                dailyTechnicals.setLow(dailyDataEntity.getTimeSeriesDaily().getLow());
                dailyTechnicals.setClose(dailyDataEntity.getTimeSeriesDaily().getClose());
                dailyTechnicals.setEma(dailyDataEntity.getEmaEntity().getEma());
                dailyTechnicals.setMacdHist(dailyDataEntity.getMacdEntity().getMacdHist());
                dailyTechnicals.setRealLowerBand(dailyDataEntity.getBbandsEntity().getRealLowerBand());
                dailyTechnicals.setRealUpperBand(dailyDataEntity.getBbandsEntity().getRealUpperBand());
                dailyTechnicalsList.add(dailyTechnicals);
            }
        }

        //  index Zero is the most recent technicals. By going from 0 to infinite we are going backwards.
        for (int i = 0; i < dailyTechnicalsList.size() - 1; i++) {
            dailyTechnicalsList.get(i).setPreviousDaysData(dailyTechnicalsList.get(i + 1));
        }

        for (int i = 1; i < dailyTechnicalsList.size(); i++) {
            dailyTechnicalsList.get(i).setNextDaysData(dailyTechnicalsList.get(i - 1));
        }

        return dailyTechnicalsList;
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

    public DailyTechnicals getPreviousDaysData() {
        if (previousDaysData == null) {
            throw new UnsyncedDataException("Previous days technicals is null");
        }
        return previousDaysData;
    }

    public void setPreviousDaysData(DailyTechnicals previousDaysData) {
        if (previousDaysData.getDay().isAfter(day)) {
            throw new UnsyncedDataException("Incorrectly setting previousDaysData in DailyTechnicals.java");
        }
        this.previousDaysData = previousDaysData;
    }

    public DailyTechnicals getNextDaysData() {
        if (nextDaysData == null) {
            throw new UnsyncedDataException("Next days technicals is null");
        }
        return nextDaysData;
    }

    public void setNextDaysData(DailyTechnicals nextDaysData) {
        if (day.isAfter(nextDaysData.getDay())) {
            throw new UnsyncedDataException("Incorrectly setting nextDaysData in DailyTechnicals.java");
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
        return "DailyTechnicals{" +
                "day=" + day +
                ", ticker='" + ticker + '\'' +
                ", openCloseMean=" + openCloseMean +
                '}';
    }
}
