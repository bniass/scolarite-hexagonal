package com.ecole221.application.command.anneeacademique;

import java.time.LocalDate;

public record CreerAnneeAcademiqueCommand(
        int codeAnnee,
        LocalDate dateDebut,
        LocalDate dateFin,
        LocalDate dateOuvertureInscription,
        LocalDate dateFinInscription
) {}
