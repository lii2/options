package com.options.repositories;

import com.options.entities.BacktestResultEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BacktestResultRepository extends CrudRepository<BacktestResultEntity, Integer> {
}
