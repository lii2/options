package com.options.controller;

import com.options.domain.backtest.BacktestResponse;
import com.options.domain.choice.Recommendation;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/options")
public class OptionsController implements ApplicationContextAware {

    private static final String defaultTicker = "SPY";
    private static final String noNewDataFetched = "No new data added";

    private AnalyzeDataOperation analyzeDataOperation;
    private SmartPersistOperation smartPersistOperation;
    private BacktestOperation backtestOperation;
    private ApplicationContext context;

    @Autowired
    public OptionsController(
            AnalyzeDataOperation analyzeDataOperation,
            SmartPersistOperation smartPersistOperation,
            BacktestOperation backtestOperation) {
        this.analyzeDataOperation = analyzeDataOperation;
        this.smartPersistOperation = smartPersistOperation;
        this.backtestOperation = backtestOperation;
    }

    @GetMapping(value = "/quickAnalyze/{ticker}", name = "Quickly pull last recommendation for selected ticker")
    public Recommendation quickAnalyze(@PathVariable String ticker) throws Exception {
        String output = getTickerData(ticker);
        Recommendation result = null;
        List<Recommendation> recommendations = analyzeData(ticker);
        if (!recommendations.isEmpty()) {
            result = recommendations.get(recommendations.size() - 1);
        }
        return result;
    }

    @GetMapping(value = "/getData/{ticker}", name = "Fetch the data for selected ticker")
    public String getData(@PathVariable String ticker) throws Exception {
        return getTickerData(ticker);
    }

    @GetMapping(value = "/fullAnalyze/{ticker}", name = "Give full recommendation list for selected ticker")
    public List<Recommendation> fullAnalyze(@PathVariable String ticker) {
        return analyzeData(ticker);
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

    private String getTickerData(String ticker) throws Exception {
        if (ticker == null || StringUtils.isBlank(ticker))
            ticker = defaultTicker;
        String result = smartPersistOperation.execute(ticker);
        return result.isEmpty()
                ? noNewDataFetched
                : result;
    }

    private List<Recommendation> analyzeData(String ticker) {
        if (ticker == null || StringUtils.isBlank(ticker))
            ticker = defaultTicker;
        analyzeDataOperation.setDaysOfData(100);
        return analyzeDataOperation.execute(ticker);
    }


}
