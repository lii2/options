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
public class MacdData extends EntityData {

    @EmbeddedId
    private MacdDataKey macdDataKey;
    private BigDecimal macd;
    private BigDecimal macdHist;
    private BigDecimal macdSignal;

    public MacdData(String ticker, String[] row) {
        this.macdDataKey = new MacdDataKey(parseDate(row[0]), ticker);
        this.macd = new BigDecimal(row[1]);
        this.macdHist = new BigDecimal(row[2]);
        this.macdSignal = new BigDecimal(row[3].replace("\r", ""));

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
