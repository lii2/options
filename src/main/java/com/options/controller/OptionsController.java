package com.options.controller;

import com.options.analysis.Recommendation;
import com.options.json.responses.BacktestResponse;
import com.options.operations.Analyst;
import com.options.operations.Backtester;
import com.options.operations.DatabaseAdministrator;
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

    private Analyst analyst;
    private DatabaseAdministrator databaseAdministrator;
    private Backtester backtester;
    private ApplicationContext context;

    @Autowired
    public OptionsController(
            Analyst analyst,
            DatabaseAdministrator databaseAdministrator,
            Backtester backtester) {
        this.analyst = analyst;
        this.databaseAdministrator = databaseAdministrator;
        this.backtester = backtester;
    }

    @GetMapping(value = "/quickAnalyze/{ticker}", name = "Quickly pull last recommendation for selected ticker")
    public Recommendation quickAnalyze(@PathVariable String ticker) throws Exception {
        Recommendation result = null;
        List<Recommendation> recommendations = analyst.analyzeData(100, ticker);
        if (!recommendations.isEmpty()) {
            result = recommendations.get(recommendations.size() - 1);
        }
        return result;
    }

    @GetMapping(value = "/getData/{ticker}", name = "Fetch the data for selected ticker")
    public String getData(@PathVariable String ticker) throws Exception {
        return databaseAdministrator.smartPersist(ticker);
    }

    @GetMapping(value = "/fullAnalyze/{ticker}", name = "Give full recommendation list for selected ticker")
    public List<Recommendation> fullAnalyze(@PathVariable String ticker) {
        return analyst.analyzeData(100, ticker);
    }

    @GetMapping("/backtest/{ticker}")
    public BacktestResponse backtest(@PathVariable String ticker) {
        analyst.setDaysOfData(100);
        backtester.setRecommendationList(analyst.analyzeData(ticker));
        return backtester.execute(ticker);
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
