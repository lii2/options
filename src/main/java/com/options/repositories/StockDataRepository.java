package com.options.repositories;

import com.options.entities.StockData;
import com.options.entities.StockDataKey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockDataRepository extends CrudRepository<StockData, StockDataKey> {

    @Query(value = "SELECT * FROM stock_data WHERE ticker = :symbol ORDER BY day DESC LIMIT 1", nativeQuery = true)
    StockData getLatestRecord(
            @Param("symbol") String ticker);

    @Query(value = "SELECT * FROM stock_data WHERE ticker= :symbol ORDER BY day DESC LIMIT :limits", nativeQuery = true)
    List<StockData> getLastXDays(
            @Param("symbol") String ticker,
            @Param("limits") int x);
}