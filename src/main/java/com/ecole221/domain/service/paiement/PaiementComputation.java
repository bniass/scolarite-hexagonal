package com.ecole221.domain.service.paiement;

import com.ecole221.domain.entity.paiement.StatutPaiement;

import java.math.BigDecimal;

public record PaiementComputation(
        BigDecimal montantApplique,
        StatutPaiement statut,
        boolean shouldApply
) {

    public static PaiementComputation full(BigDecimal montant) {
        return new PaiementComputation(
                montant,
                StatutPaiement.PAYE,
                true
        );
    }

    public static PaiementComputation advance(BigDecimal montant) {
        return new PaiementComputation(
                montant,
                StatutPaiement.AVANCE,
                true
        );
    }

    public static PaiementComputation complement(BigDecimal montant) {
        return new PaiementComputation(
                montant,
                StatutPaiement.PAYE,
                true
        );
    }

    public static PaiementComputation none() {
        return new PaiementComputation(
                BigDecimal.ZERO,
                null,
                false
        );
    }
}