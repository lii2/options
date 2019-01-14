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
public class BbandData extends EntityData {

    @EmbeddedId
    private BbandDataKey bbandDataKey;

    private BigDecimal realLowerBand;

    private BigDecimal realMiddleBand;

    private BigDecimal realUpperBand;

    public BbandData(String ticker, String[] row) {
        this.bbandDataKey = new BbandDataKey(parseDate(row[0]), ticker);
        this.realLowerBand = new BigDecimal(row[1]);
        this.realMiddleBand = new BigDecimal(row[2]);
        this.realUpperBand = new BigDecimal(row[3].replace("\r", ""));
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
