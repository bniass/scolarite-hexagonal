package com.ecole221.domain.exception;

public class TransfertNonAutoriseException extends RuntimeException {
    public TransfertNonAutoriseException(String matricule, String annee) {
        super(String.format(
                "Transfert non autorisé pour l'étudiant %s sur l'année %s (délai dépassé).",
                matricule, annee
        ));
    }
}
