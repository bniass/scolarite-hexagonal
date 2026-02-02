package com.ecole221.infrastructure.projection.inscription.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "annee_academique_projection")
public class AnneeAcademiqueProjection {

    @Id
    @Column(length = 10)
    private String anneeAcademiqueCode; // ex: "2025-2026"

    private boolean inscriptionsOuvertes;

    private LocalDate dateDebutInscriptions;
    private LocalDate dateFinInscriptions;

    protected AnneeAcademiqueProjection() {
        // JPA
    }

    public AnneeAcademiqueProjection(String anneeAcademiqueCode) {
        this.anneeAcademiqueCode = anneeAcademiqueCode;
        this.inscriptionsOuvertes = false;
    }

    public void initialiserDateInscription(LocalDate debut, LocalDate fin) {
        this.dateDebutInscriptions = debut;
        this.dateFinInscriptions = fin;
    }

    public void ouvrir() {
        this.inscriptionsOuvertes = true;
    }

    public void fermer() {
        this.inscriptionsOuvertes = false;
    }

    // getters si nécessaires (lecture uniquement)
}
