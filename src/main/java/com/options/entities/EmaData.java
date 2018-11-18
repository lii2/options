package com.options.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Entity
public class EmaData {

    @EmbeddedId
    private EmaDataKey emaDataKey;

    private double ema;

    private transient static SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");

    public EmaData() {
    }

    public EmaData(String ticker, String[] row) throws ParseException {
        this.emaDataKey = new EmaDataKey(smf.parse(row[0]), ticker);
        this.ema = Double.valueOf(row[1]);
    }

    public EmaData(EmaDataKey emaDataKey, double ema) {
        this.emaDataKey = emaDataKey;
        this.ema = ema;
    }

    public EmaDataKey getEmaDataKey() {
        return emaDataKey;
    }

    public void setEmaDataKey(EmaDataKey emaDataKey) {
        this.emaDataKey = emaDataKey;
    }

    public double getEma() {
        return ema;
    }

    public void setEma(double ema) {
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
