package com.example.career;

import com.example.career.domain.CareerHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareerHistoryRepository extends MongoRepository<CareerHistory, String> {
}
