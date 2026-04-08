package com.ecole221.infrastructure.web.paiement.request;

import com.ecole221.domain.entity.paiement.CanalPaiement;
import com.ecole221.infrastructure.web.validation.PaiementRequest;
import com.ecole221.infrastructure.web.validation.ValidPaiement;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.time.LocalTime;

@ValidPaiement
public record DeclarerPaiementRequest(
        @NotBlank
        String matricule,
        @NotBlank
        String anneeAcademique,
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
) implements PaiementRequest  {}

