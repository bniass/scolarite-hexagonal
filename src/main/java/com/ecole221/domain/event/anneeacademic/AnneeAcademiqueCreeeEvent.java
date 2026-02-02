package com.ecole221.domain.event.anneeacademic;

import com.ecole221.domain.entity.academic.AnneeAcademique;
import com.ecole221.domain.entity.academic.AnneeAcademiqueId;

public class AnneeAcademiqueCreeeEvent extends AnneeAcademiqueEvent {

    public AnneeAcademiqueCreeeEvent(AnneeAcademique annee) {
        super(annee);
    }
}

