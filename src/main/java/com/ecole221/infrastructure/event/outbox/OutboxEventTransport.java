package com.ecole221.infrastructure.event.outbox;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OutboxEventTransport {

    public void send(String payload) {
        // Simule ouh@Kafka
        log.info("OUTBOX EVENT SENT : {}", payload);
    }
}
