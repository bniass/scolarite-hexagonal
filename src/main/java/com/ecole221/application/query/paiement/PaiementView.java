package com.ecole221.application.query.paiement;

import com.ecole221.domain.entity.paiement.CanalPaiement;

import java.time.LocalDate;
import java.time.LocalTime;

public record PaiementView(
        String typePaiement,
        CanalPaiement canalPaiement,
        double montantPaye,
        LocalDate datePaiement,
        String mois,
        String annee
) {
}
