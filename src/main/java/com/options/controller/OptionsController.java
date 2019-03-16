package com.options.controller;

import com.options.agents.Analyst;
import com.options.agents.DatabaseAdministrator;
import com.options.agents.Tester;
import com.options.analysis.Recommendation;
import com.options.clients.alphavantage.AlphaVantageClient;
import com.options.clients.alphavantage.AlphaVantageDataPackage;
import com.options.json.responses.BacktestResponse;
import com.options.json.responses.FullAnalyzeResponse;
import com.options.json.responses.GetDataResponse;
import com.options.json.responses.QuickAnalyzeResponse;
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
        List<Recommendation> recommendations = analyst.analyzeData(100, ticker);
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
        return new FullAnalyzeResponse(analyst.analyzeData(100, ticker));
    }

    @GetMapping("/backtest/{ticker}")
    public BacktestResponse backtest(@PathVariable String ticker) {

        // TODO: GET list of recommendation Strategies

        // TODO:
        analyst.setDaysOfData(100);
        tester.setRecommendationList(analyst.analyzeData(ticker));
        // TODO: Tester shouldn't spit out a backtest response, couples a json response to an agent. Need to refactor.
        return tester.backtest(ticker);
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
