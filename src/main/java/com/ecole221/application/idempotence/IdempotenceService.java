package com.ecole221.application.idempotence;



import com.ecole221.infrastructure.persistence.idempotence.ProcessedEventJpaEntity;
import com.ecole221.infrastructure.persistence.idempotence.ProcessedEventJpaRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class IdempotenceService {

    private final ProcessedEventJpaRepository repository;

    public IdempotenceService(ProcessedEventJpaRepository repository) {
        this.repository = repository;
    }

    public boolean alreadyProcessed(String eventId) {
        return repository.existsById(eventId);
    }

    public void markAsProcessed(String eventId) {
        repository.save(
                new ProcessedEventJpaEntity(eventId, Instant.now())
        );
    }
}

