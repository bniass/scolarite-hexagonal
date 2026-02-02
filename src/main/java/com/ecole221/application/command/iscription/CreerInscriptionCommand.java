package com.ecole221.application.command.iscription;

import java.time.LocalDate;

public record CreerInscriptionCommand(
        String codeClasse,
        String matricule,
        String anneeAcademique,
        // données étudiant (optionnelles)
        String nom,
        String prenom,
        LocalDate dateNaissance
) {}
