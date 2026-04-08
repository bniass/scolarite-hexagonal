package com.ecole221.infrastructure.web.inscription.request;

import com.ecole221.application.command.paiement.DeclarerPaiementCommand;
import com.ecole221.domain.entity.paiement.CanalPaiement;
import com.ecole221.domain.entity.paiement.NombreDeMois;
import com.ecole221.infrastructure.web.validation.PaiementRequest;
import com.ecole221.infrastructure.web.validation.ValidPaiement;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.time.LocalTime;

@ValidPaiement
public record CreerInscriptionRequest(

        @NotBlank
        String codeClasse,

        @NotBlank
        String matricule,

        @NotBlank
        String anneeAcademique,

        // Données étudiant (optionnelles)
        String nom,
        String prenom,
        LocalDate dateNaissance,

        String typePaiement,
        @NotNull
        CanalPaiement canalPaiement,
        String operateurMobileMoney,
        String referenceMobileMoney,
        String nomBanque,
        String referenceBancaire,
        @Positive
        double montantPaye,
        LocalDate datePaiement,
        LocalTime heurePaiement,
        String preuvePaiement,
        @Positive
        Integer nombreDeMois
) implements PaiementRequest {}
