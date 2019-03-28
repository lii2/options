package com.options.repositories;

import com.options.entities.RecommendationStrategyEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationStrategyRepository extends CrudRepository<RecommendationStrategyEntity, Integer> {

    @Query(value = "SELECT * FROM recommendation_strategy", nativeQuery = true)
    List<RecommendationStrategyEntity> getAll();
}
