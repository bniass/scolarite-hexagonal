package com.ecole221.domain.event.paiement;

import com.ecole221.domain.entity.inscription.InscriptionId;
import com.ecole221.domain.event.shared.DomainEvent;

import java.time.LocalDateTime;

public class PaiementInitialValideEvent implements DomainEvent{
    private final InscriptionId inscriptionId;
    private final String sagaId;

    public PaiementInitialValideEvent(InscriptionId inscriptionId, String sagaId) {
        this.inscriptionId = inscriptionId;
        this.sagaId = sagaId;
    }

    @Override
    public String aggregateId() {
        return "";
    }

    @Override
    public String aggregateType() {
        return "";
    }

    @Override
    public LocalDateTime occurredAt() {
        return null;
    }
}
