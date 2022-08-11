package com.mediscreen.history.repository;

import com.mediscreen.history.model.History;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HistoryRepository extends MongoRepository<History, String> {
}
