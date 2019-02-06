package com.options.repositories;

import com.options.entities.TimeSeriesDailyEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeSeriesDailyRepository extends CrudRepository<TimeSeriesDailyEntity, Integer> {
}
