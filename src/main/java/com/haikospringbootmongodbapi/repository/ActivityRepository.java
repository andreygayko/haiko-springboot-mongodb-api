package com.haikospringbootmongodbapi.repository;

import com.haikospringbootmongodbapi.repository.model.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends MongoRepository<Activity, Long> {
}
