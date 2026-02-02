package com.ecole221.infrastructure.persistence.outbox.mapper;

import com.ecole221.domain.event.shared.DomainEvent;
import com.ecole221.infrastructure.persistence.outbox.dto.OutboxEventPayload;
import com.ecole221.infrastructure.persistence.outbox.entity.OutboxEventJpaEntity;
import com.ecole221.infrastructure.persistence.outbox.exception.OutboxSerializationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxEventMapper {

    private final ObjectMapper objectMapper;

    public OutboxEventJpaEntity toJpa(DomainEvent event) {

        try {
            OutboxEventPayload payload = from(event);

            return new OutboxEventJpaEntity(
                    payload.aggregateType(),
                    payload.aggregateId(),
                    payload.eventType(),
                    objectMapper.writeValueAsString(payload),
                    payload.occurredAt()
            );

        } catch (JsonProcessingException e) {
            throw new OutboxSerializationException(
                    "Erreur de sérialisation de l'événement " + event.getClass().getSimpleName(),
                    e
            );
        }
    }


    public static OutboxEventPayload from(DomainEvent event) {
        return new OutboxEventPayload(
                event.aggregateType(),
                event.aggregateId(),
                event.getClass().getSimpleName(),
                event.occurredAt()
        );
    }
}
