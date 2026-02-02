package com.ecole221.domain.event.anneeacademic;

import com.ecole221.domain.entity.academic.AnneeAcademique;
import com.ecole221.domain.entity.academic.AnneeAcademiqueId;

public class InscriptionsSuspenduesEvent extends AnneeAcademiqueEvent {

    public InscriptionsSuspenduesEvent(AnneeAcademique annee) {
        super(annee);
    }
}
