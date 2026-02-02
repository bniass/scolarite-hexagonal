package com.ecole221.domain.shared;

import java.io.Serializable;
import java.util.Objects;

public abstract class EntityId<T> implements Serializable {

    private final T value;

    protected EntityId(T value) {
        this.value = Objects.requireNonNull(value, "ID obligatoire");
    }

    public T value() {
        return value;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityId<?> that = (EntityId<?>) o;
        return value.equals(that.value);
    }

    @Override
    public final int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
