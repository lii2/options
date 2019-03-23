package com.options.repositories;

import com.options.entities.RecommendationStrategyVersionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendationStrategyVersionRepository extends CrudRepository<RecommendationStrategyVersionEntity, Integer> {
}
