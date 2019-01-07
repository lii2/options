package com.options.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class EmaData extends EntityData {

    @EmbeddedId
    private EmaDataKey emaDataKey;

    private BigDecimal ema;

    public EmaData() {
    }

    public EmaData(String ticker, String[] row) {
        this.emaDataKey = new EmaDataKey(parseDate(row[0]), ticker);
        this.ema = new BigDecimal(row[1].replace("\r", ""));
    }

    public EmaData(EmaDataKey emaDataKey, BigDecimal ema) {
        this.emaDataKey = emaDataKey;
        this.ema = ema;
    }

    public EmaDataKey getEmaDataKey() {
        return emaDataKey;
    }

    public void setEmaDataKey(EmaDataKey emaDataKey) {
        this.emaDataKey = emaDataKey;
    }

    public BigDecimal getEma() {
        return ema;
    }

    public void setEma(BigDecimal ema) {
        this.ema = ema;
    }

    @Override
    public String toString() {
        return "EmaData{" +
                "emaDataKey=" + emaDataKey +
                ", ema=" + ema +
                '}';
    }
}
