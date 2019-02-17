package com.options.repositories;

import com.options.entities.DailyDataEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface DailyDataRepository extends CrudRepository<DailyDataEntity, Integer> {

    Optional<DailyDataEntity> findByDay(LocalDate day);
}
