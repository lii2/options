package com.options.repositories;

import com.options.entities.RecommendationStrategyEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendationStrategyRepository extends CrudRepository<RecommendationStrategyEntity, Integer> {
}
