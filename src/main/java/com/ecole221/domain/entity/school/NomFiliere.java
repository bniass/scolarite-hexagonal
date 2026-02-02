package com.ecole221.domain.entity.school;

import com.ecole221.domain.shared.EntityId;

public final class NomFiliere extends EntityId<String> {
    public NomFiliere(String value) {
        super(value);
        value = value.trim().replaceAll("\\s+", " ").toUpperCase();
    }
}