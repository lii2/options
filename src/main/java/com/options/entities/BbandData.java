package com.options.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class BbandData extends EntityData {

    @EmbeddedId
    private BbandDataKey bbandDataKey;

    private BigDecimal realLowerBand;

    private BigDecimal realMiddleBand;

    private BigDecimal realUpperBand;

    public BbandData() {
    }

    public BbandData(String ticker, String[] row) {
        this.bbandDataKey = new BbandDataKey(parseDate(row[0]), ticker);
        this.realLowerBand = new BigDecimal(row[1]);
        this.realMiddleBand = new BigDecimal(row[2]);
        this.realUpperBand = new BigDecimal(row[3].replace("\r", ""));
    }

    public BbandDataKey getBbandDataKey() {
        return bbandDataKey;
    }

    public void setBbandDataKey(BbandDataKey bbandDataKey) {
        this.bbandDataKey = bbandDataKey;
    }

    public BigDecimal getRealLowerBand() {
        return realLowerBand;
    }

    public void setRealLowerBand(BigDecimal realLowerBand) {
        this.realLowerBand = realLowerBand;
    }

    public BigDecimal getRealUpperBand() {
        return realUpperBand;
    }

    public void setRealUpperBand(BigDecimal realUpperBand) {
        this.realUpperBand = realUpperBand;
    }

    public BigDecimal getRealMiddleBand() {
        return realMiddleBand;
    }

    public void setRealMiddleBand(BigDecimal realMiddleBand) {
        this.realMiddleBand = realMiddleBand;
    }

    @Override
    public String toString() {
        return "BbandData{" +
                "bbandDataKey=" + bbandDataKey +
                ", realLowerBand=" + realLowerBand +
                ", realUpperBand=" + realUpperBand +
                ", realMiddleBand=" + realMiddleBand +
                '}';
    }
}
