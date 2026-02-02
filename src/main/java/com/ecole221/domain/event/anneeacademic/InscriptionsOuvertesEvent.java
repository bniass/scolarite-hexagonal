package com.ecole221.domain.event.anneeacademic;

import com.ecole221.domain.entity.academic.AnneeAcademique;
import com.ecole221.domain.entity.academic.AnneeAcademiqueId;

import java.time.LocalDate;

public final class InscriptionsOuvertesEvent
        extends AnneeAcademiqueEvent {

    public InscriptionsOuvertesEvent(
            AnneeAcademique annee,
            LocalDate dateDebutInscriptions,
            LocalDate dateFinInscriptions) {

        super(annee);
    }
}
