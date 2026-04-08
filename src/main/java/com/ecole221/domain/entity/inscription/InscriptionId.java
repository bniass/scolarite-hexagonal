package com.ecole221.domain.entity.inscription;

import com.ecole221.domain.entity.academic.AnneeAcademique;
import com.ecole221.domain.entity.academic.AnneeAcademiqueId;
import com.ecole221.domain.entity.student.Matricule;
import com.ecole221.domain.shared.EntityId;

import java.io.Serializable;
import java.util.Objects;

public final class InscriptionId implements Serializable {

    private final Matricule matricule;
    private final AnneeAcademiqueId anneeAcademiqueId;

    public InscriptionId(Matricule matricule, AnneeAcademiqueId anneeAcademiqueId) {
        this.matricule = Objects.requireNonNull(matricule);
        this.anneeAcademiqueId = Objects.requireNonNull(anneeAcademiqueId);
    }

    public Matricule getMatricule() {
        return matricule;
    }

    public AnneeAcademiqueId getAnneeAcademiqueId() {
        return anneeAcademiqueId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InscriptionId)) return false;
        InscriptionId that = (InscriptionId) o;
        return matricule.equals(that.matricule)
                && anneeAcademiqueId.equals(that.anneeAcademiqueId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricule, anneeAcademiqueId);
    }

    @Override
    public String toString() {
        return matricule.value() + "_" + anneeAcademiqueId;
    }
}
