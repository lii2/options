package com.options.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BacktestResultRepository extends CrudRepository<BacktestResultRepository, Integer> {
}
