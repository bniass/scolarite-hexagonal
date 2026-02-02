package com.ecole221.infrastructure.persistence.outbox.entity;

public enum OutboxStatus {
    PENDING,
    PUBLISHED,
    FAILED
}
