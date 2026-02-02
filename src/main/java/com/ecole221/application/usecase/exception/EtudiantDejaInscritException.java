package com.ecole221.application.usecase.exception;

public class EtudiantDejaInscritException extends RuntimeException{

    public EtudiantDejaInscritException(String matricule, String anneeAcademique) {
        super(String.format(
                "L'étudiant avec le matricule %s est déjà inscrit pour l'année académique %s.",
                matricule, anneeAcademique
        ));
    }
}
