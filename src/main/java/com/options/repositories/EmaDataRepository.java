package com.options.repositories;

import com.options.entities.EmaData;
import com.options.entities.EmaDataKey;
import com.options.entities.StockData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmaDataRepository extends CrudRepository<EmaData, EmaDataKey> {

    @Query(value = "SELECT * FROM ema_data ORDER BY day DESC LIMIT 1", nativeQuery = true)
    EmaData getLatestRecord();

    @Query(value = "SELECT * FROM ema_data  ORDER BY day DESC LIMIT 30", nativeQuery = true)
    List<EmaData> getLastThirtyDays();
}