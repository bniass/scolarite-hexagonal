package com.ecole221.application.command.iscription;

import com.ecole221.application.command.paiement.DeclarerPaiementCommand;
import com.ecole221.domain.entity.paiement.CanalPaiement;
import com.ecole221.domain.entity.paiement.NombreDeMois;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreerInscriptionCommand(
        String codeClasse,
        String matricule,
        String anneeAcademique,
        // données étudiant (optionnelles)
        String nom,
        String prenom,
        LocalDate dateNaissance,
        String typePaiement,
        CanalPaiement canalPaiement,
        String operateurMobileMoney,
        String referenceMobileMoney,
        String nomBanque,
        String referenceBancaire,
        double montantPaye,
        LocalDate datePaiement,
        LocalTime heurePaiement,
        String preuvePaiement,
        Integer nombreDeMois
) {}
