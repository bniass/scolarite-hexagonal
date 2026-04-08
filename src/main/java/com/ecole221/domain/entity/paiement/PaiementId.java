package com.ecole221.domain.entity.paiement;

import com.ecole221.domain.shared.EntityId;

import java.util.UUID;

public final class PaiementId extends EntityId<UUID> {

    public PaiementId(UUID value) {
        super(value);
    }

    public static PaiementId generate() {
        return new PaiementId(UUID.randomUUID());
    }
}

