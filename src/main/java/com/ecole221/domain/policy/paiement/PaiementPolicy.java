package com.ecole221.domain.policy.paiement;


import com.ecole221.domain.entity.paiement.Paiement;
import com.ecole221.domain.entity.paiement.StatutPaiement;
import com.ecole221.domain.service.paiement.PaiementComputation;

import java.math.BigDecimal;
import java.util.List;

public final class PaiementPolicy {

    private PaiementPolicy() {
    }

    public static PaiementComputation compute(
            Paiement paiement,
            BigDecimal montantRestant,
            BigDecimal mensualite,
            BigDecimal demiMensualite
    ) {

        if (paiement.getStatut() == StatutPaiement.AVANCE) {

            BigDecimal resteDu = paiement.getMontantPrevu()
                    .valeur()
                    .subtract(paiement.getMontant().valeur());

            if (montantRestant.compareTo(resteDu) >= 0) {
                return PaiementComputation.complement(resteDu);
            }

            return PaiementComputation.none();
        }

        if (montantRestant.compareTo(mensualite) >= 0) {
            return PaiementComputation.full(mensualite);
        }

        if (montantRestant.compareTo(demiMensualite) >= 0) {
            return PaiementComputation.advance(demiMensualite);
        }

        return PaiementComputation.none();
    }

    public static BigDecimal calculerTotalRestantDu(List<Paiement> paiements) {
        return paiements.stream()
                .map(paiement ->
                        paiement.getMontantPrevu().valeur()
                                .subtract(paiement.getMontant().valeur())
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
