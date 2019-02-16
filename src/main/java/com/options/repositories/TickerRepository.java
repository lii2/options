package com.options.repositories;

import com.options.entities.TickerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TickerRepository extends CrudRepository<TickerEntity, Integer> {

    Optional<TickerEntity> findByTickerSymbol(String tickerSymbol);
}
