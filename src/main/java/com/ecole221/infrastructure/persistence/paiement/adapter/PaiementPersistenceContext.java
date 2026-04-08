package com.ecole221.infrastructure.persistence.paiement.adapter;

import com.ecole221.domain.entity.paiement.CanalPaiement;
import com.ecole221.domain.entity.school.Montant;

import java.time.LocalTime;
import java.util.UUID;

public record PaiementPersistenceContext(
        // Identité
        UUID paiementId,
        int totalApapyer,
        // Canal & preuves (technique)
        CanalPaiement canalPaiement,
        String operateurMobileMoney,
        String referenceMobileMoney,
        String nomBanque,
        String referenceBancaire,
        String urlPreuve,
        LocalTime heurePaiement
) {}
