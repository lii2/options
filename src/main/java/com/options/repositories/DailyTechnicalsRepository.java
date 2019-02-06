package com.options.repositories;

import com.options.entities.DailyTechnicalsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyTechnicalsRepository extends CrudRepository<DailyTechnicalsEntity, Integer> {
}
