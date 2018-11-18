package com.options.operations;

import com.options.domain.alphavantage.AlphaVantageClient;

public class CalculateRecommendationOperation {

    AlphaVantageClient alphaVantageClient;

    public CalculateRecommendationOperation() {
        alphaVantageClient = new AlphaVantageClient();
    }

    public String execute() {
        getData();
        return recommend();
    }

    private void getData() {

    }

    private String recommend() {
        return "Not yet implemented";
    }
}
