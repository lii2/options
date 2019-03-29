package com.options.controller;

import com.options.agents.Analyst;
import com.options.agents.DatabaseAdministrator;
import com.options.agents.Tester;
import com.options.analysis.Recommendation;
import com.options.analysis.RecommendationStrategy;
import com.options.clients.alphavantage.AlphaVantageClient;
import com.options.clients.alphavantage.AlphaVantageDataPackage;
import com.options.json.responses.*;
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
    private Tester tester;
    private ApplicationContext context;
    private AlphaVantageClient alphaVantageClient;

    @Autowired
    public OptionsController(
            Analyst analyst,
            DatabaseAdministrator databaseAdministrator,
            Tester tester,
            AlphaVantageClient alphaVantageClient) {
        this.analyst = analyst;
        this.databaseAdministrator = databaseAdministrator;
        this.tester = tester;
        this.alphaVantageClient = alphaVantageClient;
    }

    @GetMapping(value = "/quickAnalyze/{ticker}", name = "Quickly pull last recommendation for selected ticker")
    public QuickAnalyzeResponse quickAnalyze(@PathVariable String ticker) throws Exception {
        Recommendation result = null;
        analyst.setDailyDataList(databaseAdministrator.getDailyData(ticker));
        List<Recommendation> recommendations = analyst.analyzeData();
        if (!recommendations.isEmpty()) {
            result = recommendations.get(recommendations.size() - 1);
        }
        return new QuickAnalyzeResponse(result);
    }

    @GetMapping(value = "/getData/{ticker}", name = "Fetch the data for selected ticker")
    public GetDataResponse getData(@PathVariable String ticker) throws Exception {
        AlphaVantageDataPackage dataPackage = alphaVantageClient.getAlphaVantageDataPackage(ticker);
        return new GetDataResponse(databaseAdministrator.smartPersist(dataPackage, ticker));
    }

    @GetMapping(value = "/fullAnalyze/{ticker}", name = "Give full recommendation list for selected ticker")
    public FullAnalyzeResponse fullAnalyze(@PathVariable String ticker) {
        analyst.setDailyDataList(databaseAdministrator.getDailyData(ticker));
        return new FullAnalyzeResponse(analyst.analyzeData());
    }

    @GetMapping("/backtest/{ticker}")
    public BacktestResponse backtest(@PathVariable String ticker) {

        // TODO: GET list of recommendation Strategies

        // TODO:
        analyst.setDailyDataList(databaseAdministrator.getDailyData(ticker));
        tester.setRecommendationList(analyst.analyzeData());
        // TODO: Tester shouldn't spit out a backtest response, couples a json response to an agent. Need to refactor.
        return tester.backtest(ticker);
    }

    @GetMapping
    public GetRecommendationStrategiesResponse getRecommendationStrategies() {
        return new GetRecommendationStrategiesResponse(databaseAdministrator.getRecommendationStrategies());
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
