package com.options.repositories;

import com.options.entities.DailyDataEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailyDataRepository extends CrudRepository<DailyDataEntity, Integer> {

    Optional<DailyDataEntity> findByDay(LocalDate day);

    @Query(value = "SELECT * FROM daily_data WHERE ticker_key= :tickerKey and day = :day ORDER BY day DESC", nativeQuery = true)
    Optional<DailyDataEntity> findByDayAndTickerKey(@Param(
            "day") LocalDate day,
                                                    @Param("tickerKey") Integer tickerKey);

    @Query(value = "SELECT * FROM daily_data WHERE ticker_key= :tickerKey ORDER BY day DESC LIMIT :limits", nativeQuery = true)
    List<DailyDataEntity> findLastXDaysByTickerKey(
            @Param("tickerKey") int tickerKey,
            @Param("limits") int x);
}
