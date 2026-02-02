package com.ecole221.domain.shared;

public interface AggregateRoot<ID extends EntityId<?>> {
    ID getId();
}
