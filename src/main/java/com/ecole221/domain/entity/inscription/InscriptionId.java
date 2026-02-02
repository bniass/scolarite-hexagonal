package com.ecole221.domain.entity.inscription;

import com.ecole221.domain.entity.academic.AnneeAcademique;
import com.ecole221.domain.entity.student.Matricule;
import com.ecole221.domain.shared.EntityId;

import java.util.Objects;

public final class InscriptionId extends EntityId<String> {

    private final Matricule matricule;
    private final AnneeAcademique anneeAcademique;

    public InscriptionId(Matricule matricule, AnneeAcademique anneeAcademique) {
        super(matricule.value() + "_" + anneeAcademique.getId());
        this.matricule = Objects.requireNonNull(matricule);
        this.anneeAcademique = Objects.requireNonNull(anneeAcademique);
    }

    public Matricule getMatricule() {
        return matricule;
    }

    public AnneeAcademique getAnneeAcademique() {
        return anneeAcademique;
    }

    @Override
    public String toString() {
        return matricule.value() + "_" + anneeAcademique;
    }
}
