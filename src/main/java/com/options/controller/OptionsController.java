package com.options.controller;

import com.options.domain.alphavantage.AlphaVantageClient;
import com.options.entities.StockData;
import com.options.operations.CalculateRecommendationOperation;
import com.options.repositories.StockDataRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OptionsController implements ApplicationContextAware {

    @Autowired
    private CalculateRecommendationOperation calculateRecommendationOperation;

    private ApplicationContext context;

    @GetMapping("/recommendation")
    public String getRecommendation() throws Exception {

        // Determine what to do

        // Determine the goals

        // Determine the time line

        // Determine the risk management strategy
        return calculateRecommendationOperation.execute();
    }

    @GetMapping("/shutdownContext")
    public void shutdownContext() {
        ((ConfigurableApplicationContext) context).close();
    }

    @GetMapping("/analysis")
    public String analysis(){

        return "Not yet implemented";
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.context = ctx;

    }
}
