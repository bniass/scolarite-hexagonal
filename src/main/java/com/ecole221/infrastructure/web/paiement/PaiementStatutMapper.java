package com.ecole221.infrastructure.web.paiement;

import com.ecole221.domain.entity.paiement.StatutPaiement;
import com.ecole221.infrastructure.persistence.paiement.entity.StatutJpaPaiement;

public class PaiementStatutMapper {

    public static StatutJpaPaiement toJpa(StatutPaiement statut) {
        return StatutJpaPaiement.valueOf(statut.name());
    }

    public static StatutPaiement toDomain(StatutJpaPaiement statut) {
        return StatutPaiement.valueOf(statut.name());
    }
}
