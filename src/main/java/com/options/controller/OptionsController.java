package com.options.controller;

import com.options.domain.alphavantage.AlphaVantageClient;
import com.options.entities.StockData;
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
    private StockDataRepository stockDataRepository;

    private ApplicationContext context;

    @GetMapping("/print/database")
    public String print() {
        Iterable<StockData> stockDataIterable = stockDataRepository.findAll();
        String response = "";

        for (StockData data : stockDataIterable) {
            response = response + data.toString() + " ";
        }

        return response;
    }

    @GetMapping("/SPY/persist/100Days")
    public void persistLast100Days() throws Exception {
        AlphaVantageClient client = new AlphaVantageClient();
        List<StockData> stockDataList = client.getLast100DaysStockData("SPY");
        for (StockData stockData : stockDataList) {
            stockDataRepository.save(stockData);
        }
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
