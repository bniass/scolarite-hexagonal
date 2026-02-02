package com.ecole221.application.usecase.anneeacademic;

import com.ecole221.application.command.anneeacademique.CreerAnneeAcademiqueCommand;
import com.ecole221.application.command.anneeacademique.ModifierAnneeAcademiqueCommand;
import com.ecole221.domain.entity.school.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AnneeAcademiqueTestFactory {

    public static CreerAnneeAcademiqueCommand creerAnneeAcademique() {
        return new CreerAnneeAcademiqueCommand(
                2025,
                LocalDate.of(2025, 9, 1),
                LocalDate.of(2026, 5, 31),
                LocalDate.of(2025, 8, 1),
                LocalDate.of(2025, 11, 30)
        );
    }

    public static ModifierAnneeAcademiqueCommand modifierAnneeAcademique() {
        return new ModifierAnneeAcademiqueCommand(
                2025,
                LocalDate.of(2025, 10, 1),   // nouvelle date de début
                LocalDate.of(2026, 6, 30),   // nouvelle date de fin
                LocalDate.of(2025, 9, 1),
                LocalDate.of(2025, 11, 15)
        );
    }

    private static Filiere creerFiliere() {
        return new Filiere(
                new CodeFiliere("INFO"),
                new NomFiliere("Informatique")
        );
    }
}

