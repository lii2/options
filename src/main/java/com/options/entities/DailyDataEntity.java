package com.options.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class DailyDataEntity {
    public int dailyDataKey;
    public String ticker;
    public LocalDate day;

    @Override
    public String toString() {
        return "DailyDataEntity{" +
                "dailyDataKey=" + dailyDataKey +
                ", ticker='" + ticker + '\'' +
                ", day=" + day +
                '}';
    }
}
