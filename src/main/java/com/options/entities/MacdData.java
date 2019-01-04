package com.options.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.text.ParseException;

@Entity
public class MacdData extends EntityData {

    @EmbeddedId
    private MacdDataKey macdDataKey;

    private BigDecimal macd;

    private BigDecimal macdHist;

    private BigDecimal macdSignal;

    public MacdData() {
    }

    public MacdData(String ticker, String[] row) throws ParseException {
        this.macdDataKey = new MacdDataKey(parseDate(row[0]), ticker);
        this.macd = new BigDecimal(row[1]);
        this.macdHist = new BigDecimal(row[2]);
        this.macdSignal = new BigDecimal(row[3].replace("\r", ""));

    }


    public MacdData(MacdDataKey macdDataKey, BigDecimal macd, BigDecimal macdHist, BigDecimal macdSignal) {
        this.macdDataKey = macdDataKey;
        this.macd = macd;
        this.macdHist = macdHist;
        this.macdSignal = macdSignal;
    }

    public MacdDataKey getMacdDataKey() {
        return macdDataKey;
    }

    public void setMacdDataKey(MacdDataKey macdDataKey) {
        this.macdDataKey = macdDataKey;
    }

    public BigDecimal getMacd() {
        return macd;
    }

    public void setMacd(BigDecimal macd) {
        this.macd = macd;
    }

    public BigDecimal getMacdHist() {
        return macdHist;
    }

    public void setMacdHist(BigDecimal macdHist) {
        this.macdHist = macdHist;
    }

    public BigDecimal getMacdSignal() {
        return macdSignal;
    }

    public void setMacdSignal(BigDecimal macdSignal) {
        this.macdSignal = macdSignal;
    }

    @Override
    public String toString() {
        return "MacdData{" +
                "macdDataKey=" + macdDataKey +
                ", macd=" + macd +
                ", macdHist=" + macdHist +
                ", macdSignal=" + macdSignal +
                '}';
    }
}
