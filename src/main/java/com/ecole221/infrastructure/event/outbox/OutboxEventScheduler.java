package com.ecole221.infrastructure.event.outbox;

import com.ecole221.infrastructure.persistence.outbox.entity.OutboxStatus;
import com.ecole221.infrastructure.persistence.outbox.repository.OutboxEventJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OutboxEventScheduler {

    private final OutboxEventJpaRepository repository;
    private final OutboxEventTransport transport;

    //@Scheduled(fixedDelay = 5000)
    @Transactional
    public void publishPendingEvents() {

        var events = repository.findTop50ByStatusInOrderByOccurredAtAsc(
                List.of(OutboxStatus.PENDING, OutboxStatus.FAILED)
        );

        for (var event : events) {
            try {
                transport.send(event.getPayload());
                event.setStatus(OutboxStatus.PUBLISHED);

            } catch (Exception ex) {
                event.setRetryCount(event.getRetryCount() + 1);
                event.setStatus(OutboxStatus.FAILED);
            }
        }
    }
}
