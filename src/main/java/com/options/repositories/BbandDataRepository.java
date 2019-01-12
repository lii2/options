package com.options.repositories;

import com.options.entities.BbandData;
import com.options.entities.BbandDataKey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BbandDataRepository extends CrudRepository<BbandData, BbandDataKey> {

    @Query(value = "SELECT * FROM bband_data WHERE ticker= :symbol ORDER BY day DESC LIMIT 1", nativeQuery = true)
    BbandData getLatestRecord(
            @Param("symbol") String ticker);

    @Query(value = "SELECT * FROM bband_data WHERE ticker= :symbol ORDER BY day DESC LIMIT :limits", nativeQuery = true)
    List<BbandData> getLastXDays(
            @Param("symbol") String ticker,
            @Param("limits") int x);
}
