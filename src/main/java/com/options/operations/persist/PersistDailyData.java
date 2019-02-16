package com.options.operations.persist;

import com.options.entities.*;
import com.options.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class PersistDailyData {

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

        TimeSeriesDailyEntity timeSeriesDailyEntity = new TimeSeriesDailyEntity(0, open, close, high, low);
        EmaEntity emaEntity = new EmaEntity(0, ema);
        MacdEntity macdEntity = new MacdEntity(macdHist, macdSignal, macd);
        BbandsEntity bbandsEntity = new BbandsEntity(realMiddleBand, realUpperBand, realLowerBand);

        DailyDataEntity dailyDataEntity = new DailyDataEntity(0, day, timeSeriesDailyEntity,
                tickerRepository.findByTickerSymbol(tickerSymbol).get(), macdEntity, bbandsEntity, emaEntity);

        dailyDataRepository.save(dailyDataEntity);
    }
}
