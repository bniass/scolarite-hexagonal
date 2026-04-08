package com.ecole221.application.saga.inscription;

public enum InscriptionSagaState {
    STARTED,
    INSCRIPTION_CREATED,
    ECHEANCIER_CREATED,
    PAIEMENT_INITIE,
    PAIEMENT_VALIDE,
    COMPLETED,
    FAILED
}
