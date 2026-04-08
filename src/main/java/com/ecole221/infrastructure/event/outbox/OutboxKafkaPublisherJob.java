package com.ecole221.infrastructure.event.outbox;

import com.ecole221.infrastructure.persistence.outbox.entity.OutboxEventJpaEntity;
import com.ecole221.infrastructure.persistence.outbox.entity.OutboxStatus;
import com.ecole221.infrastructure.persistence.outbox.repository.OutboxEventJpaRepository;
import com.ecole221.infrastructure.event.kafka.KafkaEventTransport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxKafkaPublisherJob {

    private static final String TOPIC = "ecole221-anneeacademique-events";

    private final OutboxEventJpaRepository outboxRepo;
    private final KafkaEventTransport transport;

    @Scheduled(fixedDelayString = "5000")
    @Transactional
    public void publishPendingEvents() {

        List<OutboxEventJpaEntity> pending =
                outboxRepo.findTop50ByStatusInOrderByOccurredAtAsc(List.of(OutboxStatus.PENDING, OutboxStatus.FAILED));

        for (OutboxEventJpaEntity event : pending) {
            try {
                // key = aggregateId => ordre garanti par aggregate
                transport.send(TOPIC, event.getAggregateId(), event.getPayload());

                event.markPublished(); // status=PUBLISHED, publishedAt, etc.
            } catch (Exception ex) {
                log.error("Send failed outboxevent {}", event.getId(), ex);
                event.markFailed(ex.getMessage()); // status=FAILED, retryCount++
            }
        }
    }
}
