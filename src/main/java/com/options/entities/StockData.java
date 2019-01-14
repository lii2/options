package com.options.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Locale;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class StockData extends EntityData {

    @EmbeddedId
    private StockDataKey stockDataKey;
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;
    private BigInteger volume;

    public StockData(String ticker, String[] row){
        this.stockDataKey = new StockDataKey(parseDate(row[0]), ticker);
        this.open = new BigDecimal(row[1]);
        this.high = new BigDecimal(row[2]);
        this.low = new BigDecimal(row[3]);
        this.close = new BigDecimal(row[4]);
        this.volume = new BigInteger(row[5].replace("\r", ""));
    }

    public String getFormattedVolume() {
        return NumberFormat.getNumberInstance(Locale.US).format(volume);
    }

    @Override
    public String toString() {
        return "StockData{" +
                "stockDataKey=" + stockDataKey +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", close=" + close +
                ", volume=" + volume +
                '}';
    }
}
