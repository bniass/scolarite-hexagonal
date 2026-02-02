package com.ecole221.application.port.out.event;

import com.ecole221.domain.event.shared.DomainEvent;

import java.util.List;

public interface DomainEventPublisher {
    void publish(DomainEvent events);
}
