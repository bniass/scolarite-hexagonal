package com.ecole221.infrastructure.persistence.inscription.entity;

import com.ecole221.infrastructure.persistence.anneeacademique.entity.AnneeAcademiqueJpaEntity;
import com.ecole221.infrastructure.persistence.classe.entity.ClasseJpaEntity;
import com.ecole221.infrastructure.persistence.etudiant.entity.EtudiantJpaEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "inscription")
public class InscriptionJpaEntity {

    @EmbeddedId
    private InscriptionJpaId id;

    private LocalDate dateInscription;

    @MapsId("matricule")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matricule", nullable = false)
    private EtudiantJpaEntity etudiant;

    @MapsId("anneeAcademiqueId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "annee_academique_id", nullable = false)
    private AnneeAcademiqueJpaEntity anneeAcademique;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classe_id", nullable = false)
    private ClasseJpaEntity classe;
}
