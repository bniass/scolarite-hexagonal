package com.ecole221.domain.event.shared;

import java.time.LocalDateTime;

public interface DomainEvent {
    String aggregateId();

    String aggregateType();

    LocalDateTime occurredAt();
}
