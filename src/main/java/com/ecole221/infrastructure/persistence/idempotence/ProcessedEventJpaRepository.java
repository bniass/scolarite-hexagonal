package com.ecole221.infrastructure.persistence.idempotence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessedEventJpaRepository
        extends JpaRepository<ProcessedEventJpaEntity, String> {
}
