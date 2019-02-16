package com.options.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "ema")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class EmaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    public int emaKey;

    public BigDecimal ema;

    @Override
    public String toString() {
        return "EmaEntity{" +
                "emaKey=" + emaKey +
                ", ema=" + ema +
                '}';
    }
}
