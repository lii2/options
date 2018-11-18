package com.options.repositories;

import com.options.entities.StockData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface StockDataRepository extends CrudRepository<StockData, Date> {

}
