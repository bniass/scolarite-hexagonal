package com.ecole221.infrastructure.persistence.inscription.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class InscriptionJpaId implements Serializable {

    @Column(name = "matricule")
    private String matricule;

    @Column(name = "annee_academique_id")
    private String anneeAcademiqueId;

    protected InscriptionJpaId() {}

    public InscriptionJpaId(String matricule, String anneeAcademiqueId) {
        this.matricule = matricule;
        this.anneeAcademiqueId = anneeAcademiqueId;
    }

    // equals / hashCode OBLIGATOIRES
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InscriptionJpaId that)) return false;
        return Objects.equals(matricule, that.matricule)
                && Objects.equals(anneeAcademiqueId, that.anneeAcademiqueId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricule, anneeAcademiqueId);
    }
}
