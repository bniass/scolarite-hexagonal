package com.ecole221.application.port.out.event.inmemory;

import com.ecole221.application.port.out.event.DomainEventPublisher;
import com.ecole221.domain.event.shared.DomainEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("inmemory")
public class InMemoryDomainEventPublisher
        implements DomainEventPublisher {

    @Override
    public void publish(DomainEvent event) {
        System.out.println(
                    "[DOMAIN EVENT] " +
                            event.getClass().getSimpleName() +
                            " -> " + event
        );
    }
}

