package com.options.clients.alphavantage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlphaVantageDataPackage {

    private String[] timeSeriesData;
    private String[] emaData;
    private String[] macdData;
    private String[] bbandData;
}
