package com.options.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmaRepository extends CrudRepository<EmaRepository, Integer> {
}
