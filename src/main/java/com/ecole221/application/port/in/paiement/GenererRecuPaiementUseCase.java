package com.ecole221.application.port.in.paiement;

import com.ecole221.domain.entity.paiement.PaiementId;

public interface GenererRecuPaiementUseCase {
    byte[] execute(PaiementId paiementId);
}
