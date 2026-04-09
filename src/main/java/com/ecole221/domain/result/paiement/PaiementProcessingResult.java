package com.ecole221.domain.result.paiement;

import com.ecole221.domain.entity.paiement.Paiement;
import com.ecole221.domain.entity.paiement.PaiementId;
import com.ecole221.infrastructure.persistence.paiement.adapter.PaiementPersistenceContext;

import java.util.List;
import java.util.UUID;

public record PaiementProcessingResult(
        List<Paiement> paiements,
        List<PaiementPersistenceContext> contexts,
        List<UUID> paieds
) {}
