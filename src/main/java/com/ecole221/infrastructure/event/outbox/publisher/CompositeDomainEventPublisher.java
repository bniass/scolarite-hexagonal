package com.ecole221.infrastructure.event.outbox.publisher;

import com.ecole221.application.port.out.event.DomainEventPublisher;
import com.ecole221.domain.event.shared.DomainEvent;
import com.ecole221.infrastructure.event.SpringDomainEventPublisher;
import com.ecole221.infrastructure.event.outbox.OutboxDomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("mysql")
@Primary
@RequiredArgsConstructor
public class CompositeDomainEventPublisher implements DomainEventPublisher {

    private final OutboxDomainEventPublisher outboxPublisher;
    private final SpringDomainEventPublisher springPublisher;

    @Override
    public void publish(DomainEvent event) {
        // 1. Outbox (inter-services)
        outboxPublisher.publish(event);

        // 2. Spring event (in-process)
        springPublisher.publish(event);
    }
}

