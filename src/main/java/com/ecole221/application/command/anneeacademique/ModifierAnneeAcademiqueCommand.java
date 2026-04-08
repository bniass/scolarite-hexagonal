package com.ecole221.application.command.anneeacademique;

import java.time.LocalDate;

public record ModifierAnneeAcademiqueCommand(
        int code,
        LocalDate dateDebut,
        LocalDate dateFin,
        LocalDate dateDebutInscriptions,
        LocalDate dateFinInscriptions
) {}
