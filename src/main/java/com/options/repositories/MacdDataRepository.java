package com.options.repositories;

import com.options.entities.MacdData;
import com.options.entities.MacdDataKey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MacdDataRepository extends CrudRepository<MacdData, MacdDataKey> {

    @Query(value = "SELECT * FROM macd_data WHERE ticker= :symbol ORDER BY day DESC LIMIT 1", nativeQuery = true)
    MacdData getLatestRecord(
            @Param("symbol") String ticker);

    @Query(value = "SELECT * FROM macd_data WHERE ticker= :symbol ORDER BY day DESC LIMIT :limits", nativeQuery = true)
    List<MacdData> getLastXDays(
            @Param("symbol") String ticker,
            @Param("limits") int x);
}
