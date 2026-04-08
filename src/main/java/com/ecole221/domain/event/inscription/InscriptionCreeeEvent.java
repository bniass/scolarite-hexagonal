package com.ecole221.domain.event.inscription;

import com.ecole221.domain.entity.academic.MoisAcademique;
import com.ecole221.domain.entity.inscription.Inscription;
import com.ecole221.domain.entity.inscription.InscriptionCreationSnapshot;
import com.ecole221.domain.entity.inscription.InscriptionId;
import com.ecole221.domain.entity.paiement.PaiementSnapshot;

import java.util.List;

public class InscriptionCreeeEvent extends InscriptionEvent{
    private final InscriptionCreationSnapshot inscriptionCreationSnapshot;

    public InscriptionCreeeEvent(InscriptionCreationSnapshot inscriptionCreationSnapshot) {
        super(inscriptionCreationSnapshot.inscriptionId());
        this.inscriptionCreationSnapshot = inscriptionCreationSnapshot;
    }

    public InscriptionCreationSnapshot getInscriptionCreationSnapshot() {
        return inscriptionCreationSnapshot;
    }

    public String getSagaId() {
        return inscriptionCreationSnapshot.sagaId();
    }
}
