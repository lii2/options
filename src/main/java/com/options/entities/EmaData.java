package com.options.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Entity
public class EmaData extends Data{

    @EmbeddedId
    private EmaDataKey emaDataKey;

    private BigDecimal ema;

    private transient static SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");

    public EmaData() {
    }

    public EmaData(String ticker, String[] row) throws ParseException {
        this.emaDataKey = new EmaDataKey(smf.parse(row[0]), ticker);
        this.ema = new BigDecimal(row[1].replace("\r",""));
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
