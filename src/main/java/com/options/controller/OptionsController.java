package com.options.controller;

import com.options.domain.backtest.BacktestResponse;
import com.options.operations.AnalyzeDataOperation;
import com.options.operations.BacktestOperation;
import com.options.operations.SmartPersistOperation;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class OptionsController implements ApplicationContextAware {

    private static final String defaultTicker = "SPY";

    @Autowired
    private AnalyzeDataOperation analyzeDataOperation;

    @Autowired
    private SmartPersistOperation smartPersistOperation;

    @Autowired
    private BacktestOperation backtestOperation;

    private ApplicationContext context;

    @GetMapping("/data/{ticker}")
    public String getData(@PathVariable String ticker) throws Exception {

        if (ticker == null || StringUtils.isBlank(ticker))
            ticker = defaultTicker;
        // Get Data
        String result = smartPersistOperation.execute(ticker);

        // Determine what to do

        // Determine the goals

        // Determine the time line

        // Determine the risk management strategy
        return result.isEmpty() ? "No new data added" : result;
    }

    @GetMapping("/analysis/{ticker}")
    public String analysis(@PathVariable String ticker) {
        if (ticker == null || StringUtils.isBlank(ticker))
            ticker = defaultTicker;
        analyzeDataOperation.setDaysOfData(100);
        return Arrays.toString(analyzeDataOperation.execute(ticker).toArray());
    }

    @GetMapping("/backtest/{ticker}")
    public BacktestResponse backtest(@PathVariable String ticker) {
        analyzeDataOperation.setDaysOfData(100);
        backtestOperation.setRecommendationList(analyzeDataOperation.execute(ticker));
        return backtestOperation.execute(ticker);
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
