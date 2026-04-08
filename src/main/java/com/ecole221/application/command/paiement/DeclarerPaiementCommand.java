package com.ecole221.application.command.paiement;

import com.ecole221.domain.entity.paiement.CanalPaiement;
import com.ecole221.infrastructure.web.paiement.request.DeclarerPaiementRequest;

import java.time.LocalDate;
import java.time.LocalTime;

public record DeclarerPaiementCommand(
        String matricule,
        String anneeAcademique,
        String typePaiement,
        CanalPaiement canalPaiement,
        String operateurMobileMoney,
        String referenceMobileMoney,
        String nomBanque,
        String referenceBancaire,
        double montantPaye,
        LocalDate datePaiement,
        LocalTime heurePaiement,
        String preuvePaiement
) {
    public DeclarerPaiementCommand withPreuvePaiement(String preuvePaiement) {
        return new DeclarerPaiementCommand(
                matricule,
                anneeAcademique,
                typePaiement,
                canalPaiement,
                operateurMobileMoney,
                referenceMobileMoney,
                nomBanque,
                referenceBancaire,
                montantPaye,
                datePaiement,
                heurePaiement,
                preuvePaiement
        );
    }

    public DeclarerPaiementCommand withMontant(double montantPaye) {
        return new DeclarerPaiementCommand(
                matricule,
                anneeAcademique,
                typePaiement,
                canalPaiement,
                operateurMobileMoney,
                referenceMobileMoney,
                nomBanque,
                referenceBancaire,
                montantPaye,
                datePaiement,
                heurePaiement,
                preuvePaiement
        );
    }
}
