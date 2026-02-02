package com.ecole221.infrastructure.persistence.anneeacademique.entity;

import com.ecole221.domain.entity.academic.MoisAcademique;
import jakarta.persistence.*;


@Embeddable
public class MoisAcademiqueEmbeddable {

    private int mois;
    private int annee;

    protected MoisAcademiqueEmbeddable() {}

    public MoisAcademiqueEmbeddable(int mois, int annee) {
        this.mois = mois;
        this.annee = annee;
    }

    public static MoisAcademiqueEmbeddable fromDomain(MoisAcademique m) {
        return new MoisAcademiqueEmbeddable(m.mois(), m.annee());
    }

    public MoisAcademique toDomain() {
        return new MoisAcademique(mois, annee);
    }

    public int getMois() {
        return mois;
    }

    public int getAnnee() {
        return annee;
    }
}
