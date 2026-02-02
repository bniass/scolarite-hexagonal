package com.ecole221.application.query.anneeacademique;

import java.time.LocalDate;
import java.util.List;

public record AnneeAcademiqueView(
        String codeAnnee,
        LocalDate dateDebut,
        LocalDate dateFin,
        LocalDate dateOuvertureInscription,
        LocalDate dateFinInscription,
        List<MoisAcademiqueView> moisAnneeScolaire
) {
}
