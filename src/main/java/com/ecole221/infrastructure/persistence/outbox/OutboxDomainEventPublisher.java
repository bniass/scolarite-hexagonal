package com.ecole221.infrastructure.event.outbox;

import com.ecole221.application.port.out.event.DomainEventPublisher;
import com.ecole221.domain.event.shared.DomainEvent;
import com.ecole221.infrastructure.persistence.outbox.mapper.OutboxEventMapper;
import com.ecole221.infrastructure.persistence.outbox.repository.OutboxEventJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("mysql")
@RequiredArgsConstructor
public class OutboxDomainEventPublisher implements DomainEventPublisher {

    private final OutboxEventJpaRepository repository;
    private final OutboxEventMapper mapper;

    @Override
    public void publish(DomainEvent event) {
        repository.save(mapper. toJpa(event));
    }
}
