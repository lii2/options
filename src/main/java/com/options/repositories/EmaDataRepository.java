package com.options.repositories;

import com.options.entities.EmaData;
import com.options.entities.EmaDataKey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmaDataRepository extends CrudRepository<EmaData, EmaDataKey> {

    @Query(value = "SELECT * FROM ema_data WHERE ticker= :symbol ORDER BY day DESC LIMIT 1", nativeQuery = true)
    EmaData getLatestRecord(
            @Param("symbol") String ticker);

    @Query(value = "SELECT * FROM ema_data WHERE ticker= :symbol ORDER BY day DESC LIMIT :limits", nativeQuery = true)
    List<EmaData> getLastXDays(
            @Param("symbol") String ticker,
            @Param("limits") int x);
}