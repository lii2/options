package com.options.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class EmaData extends EntityData {

    @EmbeddedId
    private EmaDataKey emaDataKey;
    private BigDecimal ema;

    public EmaData(String ticker, String[] row) {
        this.emaDataKey = new EmaDataKey(parseDate(row[0]), ticker);
        this.ema = new BigDecimal(row[1].replace("\r", ""));
    }

    @Override
    public String toString() {
        return "EmaData{" +
                "emaDataKey=" + emaDataKey +
                ", ema=" + ema +
                '}';
    }
}
