package com.ecole221.application.result.paiement;

import com.ecole221.domain.entity.paiement.Paiement;
import com.ecole221.infrastructure.persistence.paiement.adapter.PaiementPersistenceContext;

import java.util.List;

public record PaiementResult(
        List<Paiement> paiements,
        List<PaiementPersistenceContext> contexts
) {
}
