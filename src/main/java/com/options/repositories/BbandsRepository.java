package com.options.repositories;

import com.options.entities.BbandsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BbandsRepository extends CrudRepository<BbandsEntity, Integer> {
}
