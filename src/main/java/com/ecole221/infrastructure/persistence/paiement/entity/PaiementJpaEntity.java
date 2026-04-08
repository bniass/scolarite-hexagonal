package com.ecole221.infrastructure.persistence.paiement.entity;

import com.ecole221.domain.entity.paiement.CanalPaiement;
import com.ecole221.domain.entity.paiement.StatutPaiement;
import com.ecole221.domain.entity.paiement.TypePaiement;
import com.ecole221.infrastructure.persistence.anneeacademique.entity.AnneeMoisJpaEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(
        name = "paiements"
)
public class PaiementJpaEntity {

    /* =======================
       IDENTITE
       ======================= */

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    /* =======================
       INSCRIPTION (FK)
       ======================= */

    @Column(name = "matricule", nullable = false, length = 50)
    private String matricule;

    @Column(name = "annee_academique", nullable = false, length = 12)
    private String anneeAcademique;

    /* =======================
       TYPE & CANAL
       ======================= */

    @Enumerated(EnumType.STRING)
    @Column(name = "type_paiement", nullable = false)
    private TypePaiement typePaiement;

    @Enumerated(EnumType.STRING)
    @Column(name = "canal_paiement", nullable = false)
    private CanalPaiement canalPaiement;
    // MOBILE_MONEY | BANQUE | COMPTANT

    /* =======================
       MOBILE MONEY
       ======================= */

    @Column(name = "operateur_mobile_money", length = 50)
    private String operateurMobileMoney;

    @Column(name = "reference_mobile_money", length = 100)
    private String referenceMobileMoney;

    /* =======================
       BANQUE
       ======================= */

    @Column(name = "nom_banque", length = 100)
    private String nomBanque;

    @Column(name = "reference_bancaire", length = 100)
    private String referenceBancaire;

    /* =======================
       MONTANTS
       ======================= */

    @Column(name = "montant_prevu", nullable = false)
    private double montantPrevu;

    @Column(name = "montant_paye", nullable = false)
    private double montantPaye;

    /* =======================
       MOIS ACADEMIQUE (FK)
       ======================= */

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "annee_mois_id")
    private AnneeMoisJpaEntity moisAcademique;

    /* =======================
       DATES & PREUVE
       ======================= */

    @Column(name = "date_paiement", nullable = true)
    private LocalDate datePaiement;

    @Column(name = "heure_paiement", nullable = true)
    private LocalTime heurePaiement;

    @Column(name = "chemin")
    private String chemin;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutJpaPaiement statut;

    @Lob
    @Column(name = "commentaires", columnDefinition = "LONGTEXT")
    private String commentaires;

    /* =======================
       CONSTRUCTEURS
       ======================= */

    protected PaiementJpaEntity() {
        // JPA
    }

    public PaiementJpaEntity(
            UUID id,
            String matricule,
            String anneeAcademique,
            TypePaiement typePaiement,
            CanalPaiement canalPaiement,
            String operateurMobileMoney,
            String referenceMobileMoney,
            String nomBanque,
            String referenceBancaire,
            double montantPrevu,
            double montantPaye,
            AnneeMoisJpaEntity moisAcademique,
            LocalDate datePaiement,
            LocalTime heurePaiement,
            String chemin,
            StatutJpaPaiement statutPaiement,
            String commentaires
    ) {
        this.id = id;
        this.matricule = matricule;
        this.anneeAcademique = anneeAcademique;
        this.typePaiement = typePaiement;
        this.canalPaiement = canalPaiement;
        this.operateurMobileMoney = operateurMobileMoney;
        this.referenceMobileMoney = referenceMobileMoney;
        this.nomBanque = nomBanque;
        this.referenceBancaire = referenceBancaire;
        this.montantPrevu = montantPrevu;
        this.montantPaye = montantPaye;
        this.moisAcademique = moisAcademique;
        this.datePaiement = datePaiement;
        this.heurePaiement = heurePaiement;
        this.chemin = chemin;
        this.statut = statutPaiement;
        this.commentaires = commentaires;
    }

    public void copyFrom(PaiementJpaEntity source) {

        this.matricule = source.matricule;
        this.anneeAcademique = source.anneeAcademique;

        this.typePaiement = source.typePaiement;
        this.canalPaiement = source.canalPaiement;

        this.operateurMobileMoney = source.operateurMobileMoney;
        this.referenceMobileMoney = source.referenceMobileMoney;

        this.nomBanque = source.nomBanque;
        this.referenceBancaire = source.referenceBancaire;

        this.montantPrevu = source.montantPrevu;
        this.montantPaye = source.montantPaye;

        this.moisAcademique = source.moisAcademique;

        this.datePaiement = source.datePaiement;
        this.heurePaiement = source.heurePaiement;

        this.chemin = source.chemin;

        this.statut = source.statut;
        this.commentaires += source.commentaires;
    }
}
