package com.options.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MacdDataKey implements Serializable {

    private transient static SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
    private LocalDate day;
    private String ticker;

    @Override
    public String toString() {
        return "MacdDataKey{" +
                "day=" + day +
                ", ticker='" + ticker + '\'' +
                '}';
    }
}
