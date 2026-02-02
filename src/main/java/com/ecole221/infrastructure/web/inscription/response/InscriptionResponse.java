package com.ecole221.infrastructure.web.inscription.response;

public record InscriptionResponse(
        String matricule,
        String anneeAcademique,
        String codeClasse
) {}
