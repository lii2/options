package com.options.repositories;

import com.options.entities.StockData;
import com.options.entities.StockDataKey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockDataRepository extends CrudRepository<StockData, StockDataKey> {

    @Query(value = "SELECT * FROM stock_data ORDER BY day DESC LIMIT 1", nativeQuery = true)
    StockData getLatestRecord();

    @Query(value = "SELECT * FROM stock_data ORDER BY day DESC LIMIT 30", nativeQuery = true)
    List<StockData> getLastThirtyDays();
}