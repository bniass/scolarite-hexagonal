package com.ecole221.infrastructure.persistence.outbox.exception;

public class OutboxSerializationException extends RuntimeException {

    public OutboxSerializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
