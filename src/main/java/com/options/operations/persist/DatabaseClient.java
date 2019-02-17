package com.options.operations.persist;

import com.options.entities.*;
import com.options.repositories.DailyDataRepository;
import com.options.repositories.TickerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class DatabaseClient {

    @Autowired
    TickerRepository tickerRepository;

    @Autowired
    DailyDataRepository dailyDataRepository;

    // The tickerSymbol is the key, the tickerKey is the value.
    private final Map<String, Integer> tickerMap;

    @Autowired
    public DatabaseClient(TickerRepository tickerRepository,
                          DailyDataRepository dailyDataRepository) {
        this.tickerRepository = tickerRepository;
        this.dailyDataRepository = dailyDataRepository;
        this.tickerMap = new HashMap<>();
        for (TickerEntity tickerEntity : tickerRepository.findAll()) {
            tickerMap.put(tickerEntity.getTickerSymbol(), tickerEntity.getTickerKey());
        }

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

        // TODO: Figure this out if the database ever grows to 999999 rows.
        DailyDataEntity dailyDataEntity = new DailyDataEntity(999999, day, timeSeriesDailyEntity,
                tickerRepository.findByTickerSymbol(tickerSymbol).get(), macdEntity, bbandsEntity, emaEntity);

        timeSeriesDailyEntity.setDailyDataEntity(dailyDataEntity);
        emaEntity.setDailyDataEntity(dailyDataEntity);
        macdEntity.setDailyDataEntity(dailyDataEntity);
        bbandsEntity.setDailyDataEntity(dailyDataEntity);

        dailyDataRepository.save(dailyDataEntity);
    }

    public String getDailyDataById(int id) {
        return dailyDataRepository.findById(id).get().toString();
    }

    public Optional<DailyDataEntity> getDailyDataByDay(LocalDate day) {
        return dailyDataRepository.findByDay(day);
    }

    public List<DailyDataEntity> getLast100DaysData(String tickerSymbol) {
        return dailyDataRepository.findLastXDaysByTickerKey(tickerMap.get(tickerSymbol), 100);
    }
}
