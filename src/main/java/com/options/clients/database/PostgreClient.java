package com.options.clients.database;

import com.options.entities.*;
import com.options.repositories.DailyDataRepository;
import com.options.repositories.TickerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class PostgreClient {

    @Autowired
    TickerRepository tickerRepository;

    @Autowired
    DailyDataRepository dailyDataRepository;

    @Autowired
    public PostgreClient(TickerRepository tickerRepository,
                         DailyDataRepository dailyDataRepository) {
        this.tickerRepository = tickerRepository;
        this.dailyDataRepository = dailyDataRepository;

    }

    public void persistData(String tickerSymbol, LocalDate day, BigDecimal open, BigDecimal high, BigDecimal low,
                            BigDecimal close, BigDecimal ema, BigDecimal macd, BigDecimal macdHist, BigDecimal macdSignal,
                            BigDecimal realLowerBand, BigDecimal realMiddleBand, BigDecimal realUpperBand) {
        // Save ticker symbol if not there
        if (!tickerRepository.findByTickerSymbol(tickerSymbol).isPresent()) {
            tickerRepository.save(new TickerEntity(tickerSymbol));
        }

        TimeSeriesDailyEntity timeSeriesDailyEntity = new TimeSeriesDailyEntity();
        timeSeriesDailyEntity.setOpen(open);
        timeSeriesDailyEntity.setClose(close);
        timeSeriesDailyEntity.setHigh(high);
        timeSeriesDailyEntity.setLow(low);

        EmaEntity emaEntity = new EmaEntity();
        emaEntity.setEma(ema);

        MacdEntity macdEntity = new MacdEntity();
        macdEntity.setMacdHist(macdHist);
        macdEntity.setMacdSignal(macdSignal);
        macdEntity.setMacd(macd);

        BbandsEntity bbandsEntity = new BbandsEntity();
        bbandsEntity.setRealLowerBand(realLowerBand);
        bbandsEntity.setRealMiddleBand(realMiddleBand);
        bbandsEntity.setRealUpperBand(realUpperBand);

        // Let hibernate generate key automatically
        DailyDataEntity dailyDataEntity = new DailyDataEntity(0, day, timeSeriesDailyEntity,
                tickerRepository.findByTickerSymbol(tickerSymbol).get(), macdEntity, bbandsEntity, emaEntity);

        timeSeriesDailyEntity.setDailyDataEntity(dailyDataEntity);
        emaEntity.setDailyDataEntity(dailyDataEntity);
        macdEntity.setDailyDataEntity(dailyDataEntity);
        bbandsEntity.setDailyDataEntity(dailyDataEntity);

        System.out.println(dailyDataEntity);
        dailyDataRepository.save(dailyDataEntity);
    }

    public String getDailyDataById(int id) {
        return dailyDataRepository.findById(id).get().toString();
    }

    public Optional<DailyDataEntity> getDailyDataByDayAndTicker(LocalDate day, String tickerSymbol) {
        Integer tickerKey = getTickerKey(tickerSymbol);
        return dailyDataRepository.findByDayAndTickerKey(day, tickerKey);
    }

    public List<DailyDataEntity> getLast100DaysData(String tickerSymbol) {
        return dailyDataRepository.findLastXDaysByTickerKey(getTickerKey(tickerSymbol), 100);
    }

    private int getTickerKey(String tickerSymbol) {
        for (TickerEntity tickerEntity : tickerRepository.findAll()) {
            if (tickerEntity.getTickerSymbol().equalsIgnoreCase(tickerSymbol)) {
                return tickerEntity.getTickerKey();
            }
        }
        throw new RuntimeException("Ticker symbol not found in database");
    }
}
