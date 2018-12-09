package com.options.controller;

import com.options.operations.AnalyzeDataOperation;
import com.options.operations.BacktestOperation;
import com.options.operations.SmartPersistOperation;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class OptionsController implements ApplicationContextAware {

    @Autowired
    private AnalyzeDataOperation analyzeDataOperation;

    @Autowired
    private SmartPersistOperation smartPersistOperation;

    @Autowired
    private BacktestOperation backtestOperation;

    private ApplicationContext context;

    @GetMapping("/recommendation")
    public String getRecommendation() throws Exception {

        // Get Data
        smartPersistOperation.execute();

        // Determine what to do

        // Determine the goals

        // Determine the time line
        analyzeDataOperation.setDaysOfData(30);
        // Determine the risk management strategy
        return Arrays.toString(analyzeDataOperation.execute().toArray());
    }

    @GetMapping("/analysis")
    public String analysis() {
        analyzeDataOperation.setDaysOfData(100);
        return Arrays.toString(analyzeDataOperation.execute().toArray());
    }

    @GetMapping("/backtest")
    public String backtest() {
        analyzeDataOperation.setDaysOfData(100);
        backtestOperation.setRecommendationList(analyzeDataOperation.execute());
        return backtestOperation.execute();
    }

    @GetMapping("/shutdownContext")
    public void shutdownContext() {
        ((ConfigurableApplicationContext) context).close();
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.context = ctx;

    }
}
