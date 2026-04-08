package com.ecole221.infrastructure.web.validation;

import com.ecole221.domain.entity.paiement.CanalPaiement;

public interface PaiementRequest {

    CanalPaiement canalPaiement();

    String operateurMobileMoney();

    String referenceMobileMoney();

    String nomBanque();

    String referenceBancaire();

    String preuvePaiement();
}