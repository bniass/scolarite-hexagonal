package com.ecole221.domain.event.inscription;

import com.ecole221.domain.entity.inscription.InscriptionId;
import com.ecole221.domain.event.shared.DomainEvent;

import java.time.LocalDateTime;

public abstract class InscriptionEvent implements DomainEvent {

    private final InscriptionId inscriptionId;
    private final LocalDateTime occurredAt;

    protected InscriptionEvent(InscriptionId inscriptionId) {
        this.inscriptionId = inscriptionId;
        this.occurredAt = LocalDateTime.now();
    }

    public InscriptionId getInscriptionId() {
        return inscriptionId;
    }

    @Override
    public LocalDateTime occurredAt() {
        return occurredAt;
    }

    @Override
    public String aggregateType() {
        return "Inscription";
    }

    @Override
    public String aggregateId() {
        return inscriptionId.getMatricule().value()+"_"+inscriptionId.getAnneeAcademiqueId().value();
    }
}
