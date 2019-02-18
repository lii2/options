package com.options.repositories;

import com.options.entities.MacdEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MacdRepository extends CrudRepository<MacdEntity, Integer> {
}
