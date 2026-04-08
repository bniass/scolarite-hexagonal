package com.ecole221.application.port.in.inscription;

public interface AnnulerInscriptionUseCase {
    void annuler(String matricule, String anneeAcademique, String raison);
}
