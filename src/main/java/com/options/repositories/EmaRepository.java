package com.options.repositories;

import com.options.entities.EmaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmaRepository extends CrudRepository<EmaEntity, Integer> {
}
