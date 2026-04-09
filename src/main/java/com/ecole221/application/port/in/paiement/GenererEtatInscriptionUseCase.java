package com.ecole221.application.port.in.paiement;

import com.ecole221.domain.entity.inscription.InscriptionId;

public interface GenererEtatInscriptionUseCase {
    byte[] execute(InscriptionId inscriptionId);
}
