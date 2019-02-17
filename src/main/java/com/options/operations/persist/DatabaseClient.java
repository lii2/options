package com.options.operations.persist;

import com.options.entities.*;
import com.options.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DatabaseClient {

    @Autowired
    TickerRepository tickerRepository;

    @Autowired
    DailyDataRepository dailyDataRepository;

    @Autowired
    TimeSeriesDailyRepository timeSeriesDailyRepository;

    @Autowired
    MacdRepository macdRepository;

    @Autowired
    BbandsRepository bbandsRepository;

    @Autowired
    EmaRepository emaRepository;

    public void persistData(String tickerSymbol, LocalDate day, BigDecimal open, BigDecimal close, BigDecimal high,
                            BigDecimal low, BigDecimal ema, BigDecimal macdHist, BigDecimal macdSignal, BigDecimal macd,
                            BigDecimal realMiddleBand, BigDecimal realUpperBand, BigDecimal realLowerBand) {
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

        DailyDataEntity dailyDataEntity = new DailyDataEntity(0, day, timeSeriesDailyEntity,
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
}
