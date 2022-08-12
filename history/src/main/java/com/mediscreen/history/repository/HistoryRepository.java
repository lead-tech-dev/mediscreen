package com.mediscreen.history.repository;

import com.mediscreen.history.model.History;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * HistoryRepository. Interface that allows to access and
 * persist data between history object and history-db database
 */
@Repository
public interface HistoryRepository extends MongoRepository<History, String> {
    Iterable<History> findAllByPatientId(long patientId);
}
