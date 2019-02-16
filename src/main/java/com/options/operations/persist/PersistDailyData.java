package com.options.operations.persist;

import com.options.entities.DailyDataEntity;
import com.options.entities.TickerEntity;
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
                            BigDecimal realMiddleBand, BigDecimal realUpperBand, BigDecimal realLowerBand){
        // Save ticker symbol if not there
        if(!tickerRepository.findByTickerSymbol(tickerSymbol).isPresent()){
            tickerRepository.save(new TickerEntity(tickerSymbol));
        }

        
        DailyDataEntity dailyDataEntity = new DailyDataEntity(0, day );

    }
}
