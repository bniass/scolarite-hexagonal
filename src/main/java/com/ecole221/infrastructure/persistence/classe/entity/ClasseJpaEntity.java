package com.ecole221.infrastructure.persistence.classe.entity;

import com.ecole221.infrastructure.persistence.filiere.entity.FiliereJpaEntity;
import com.ecole221.infrastructure.persistence.inscription.entity.InscriptionJpaEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "classe")
public class ClasseJpaEntity {

    @Id
    @Column(length = 15)
    private String code;

    @Column(length = 100, nullable = false)
    private String nom;

    private Integer fraisInscription;
    private Integer mensualite;
    private Integer autresFrais;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filiere_code", nullable = false)
    private FiliereJpaEntity filiere;
}