package com.ecole221.infrastructure.web.inscription.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreerInscriptionRequest(

        @NotBlank
        String codeClasse,

        @NotBlank
        String matricule,

        @NotBlank
        String anneeAcademique,

        // Données étudiant (optionnelles)
        String nom,
        String prenom,
        LocalDate dateNaissance

) {}
