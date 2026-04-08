package com.ecole221.domain.entity.paiement;

import com.ecole221.domain.entity.academic.AnneeAcademiqueId;
import com.ecole221.domain.entity.school.Montant;
import com.ecole221.domain.entity.student.Matricule;

import java.time.LocalDate;
import java.time.LocalTime;

public record PaiementSnapshot(
        Matricule matricule,
        AnneeAcademiqueId anneeAcademiqueId,
        TypePaiement typePaiement,
        CanalPaiement canalPaiement,
        String operateurMobileMoney,
        String referenceMobileMoney,
        String nomBanque,
        String referenceBancaire,
        Montant montantPaye,
        LocalDate datePaiement,
        LocalTime heurePaiement,
        String urlPreuve,
        Integer periodePaiement
) {}

