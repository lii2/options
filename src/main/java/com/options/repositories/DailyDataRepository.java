package com.options.repositories;

import com.options.entities.DailyDataEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyDataRepository extends CrudRepository<DailyDataEntity, Integer> {
}
