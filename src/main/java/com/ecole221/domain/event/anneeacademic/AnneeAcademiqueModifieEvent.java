package com.ecole221.domain.event.anneeacademic;

import com.ecole221.domain.entity.academic.AnneeAcademique;
import com.ecole221.domain.entity.academic.AnneeAcademiqueId;

public class AnneeAcademiqueModifieEvent extends AnneeAcademiqueEvent {
    public AnneeAcademiqueModifieEvent(AnneeAcademique annee) {
        super(annee);
    }
}
