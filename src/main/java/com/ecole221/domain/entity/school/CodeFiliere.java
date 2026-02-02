package com.ecole221.domain.entity.school;

import com.ecole221.domain.shared.EntityId;

public final class CodeFiliere extends EntityId<String> {
    public CodeFiliere(String value) {
        super(value);
        value = value.trim().replaceAll("\\s+", " ").toUpperCase();

        if (!value.matches("[A-Z0-9-]+( [A-Z0-9-]+)*")) {
            throw new IllegalArgumentException(value);
        }
    }

}
