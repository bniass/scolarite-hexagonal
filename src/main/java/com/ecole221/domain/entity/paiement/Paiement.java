package com.ecole221.domain.entity.paiement;

import com.ecole221.domain.entity.academic.MoisAcademique;
import com.ecole221.domain.entity.inscription.InscriptionId;
import com.ecole221.domain.entity.school.Montant;
import com.ecole221.domain.event.shared.EventAggregateRoot;
import com.ecole221.domain.exception.ScolariteException;
import com.ecole221.domain.shared.AggregateRoot;

import java.time.LocalDate;
import java.util.Objects;

public class Paiement extends EventAggregateRoot
        implements AggregateRoot<PaiementId> {

    private final PaiementId id;
    private final InscriptionId inscriptionId;
    private final LocalDate datePaiement;
    private final Montant montant;
    private final TypePaiement typePaiement;
    private final CanalPaiement canalPaiement;
    private final MoisAcademique moisAcademique;
    private Montant montantPrevu;

    private StatutPaiement statut;

    /* =======================
       CONSTRUCTEUR PRIVE
       ======================= */

    private Paiement(
            PaiementId id,
            InscriptionId inscriptionId,
            LocalDate datePaiement,
            Montant montant,
            TypePaiement typePaiement,
            CanalPaiement canalPaiement,
            MoisAcademique moisAcademique,
            Montant montantPrevu,
            StatutPaiement statut
    ) {
        this.id = Objects.requireNonNull(id);
        this.inscriptionId = Objects.requireNonNull(inscriptionId);
        this.datePaiement = Objects.requireNonNull(datePaiement);
        this.montant = Objects.requireNonNull(montant);
        this.typePaiement = Objects.requireNonNull(typePaiement);
        this.canalPaiement = canalPaiement;
        this.moisAcademique = moisAcademique;
        this.montantPrevu = montantPrevu;
        this.statut = statut;
    }

    /* =======================
       FACTORIES METIER
       ======================= */
    private static Paiement creer(
            PaiementId id,
            InscriptionId inscriptionId,
            LocalDate datePaiement,
            Montant montant,
            Montant montantPrevu,
            TypePaiement typePaiement,
            CanalPaiement canalPaiement,
            MoisAcademique moisAcademique,
            StatutPaiement statut
    ) {
        Paiement paiement = new Paiement(
                id,
                inscriptionId,
                datePaiement,
                montant,
                typePaiement,
                canalPaiement,
                moisAcademique,
                montantPrevu,
                statut
        );
        return paiement;
    }


    public static Paiement creerFraisInscription(
            PaiementId id,
            InscriptionId inscriptionId,
            LocalDate datePaiement,
            CanalPaiement canalPaiement,
            Montant montant,
            Montant montantPrevu
    ) {
        return creer(
                id,
                inscriptionId,
                datePaiement,
                montant,
                montantPrevu,
                TypePaiement.FRAIS_INSCRIPTION,
                canalPaiement,
                null,
                StatutPaiement.PAYE
        );
    }

    public static Paiement creerFraisDivers(
            PaiementId id,
            InscriptionId inscriptionId,
            LocalDate datePaiement,
            CanalPaiement canalPaiement,
            Montant montant,
            Montant montantPrevu
    ) {
        return creer(
                id,
                inscriptionId,
                datePaiement,
                montant,
                montantPrevu,
                TypePaiement.FRAIS_DIVERS,
                canalPaiement,
                null,
                StatutPaiement.PAYE
        );
    }

    public static Paiement creerPaiementMensuel(
            PaiementId id,
            InscriptionId inscriptionId,
            LocalDate datePaiement,
            Montant montant,
            CanalPaiement canalPaiement,
            Montant montantPrevu,
            MoisAcademique moisAcademique
    ) {
        return creer(
                id,
                inscriptionId,
                datePaiement,
                montant,
                montantPrevu,
                TypePaiement.MENSUALITE,
                canalPaiement,
                moisAcademique,
                StatutPaiement.PAYE
        );
    }

    public static Paiement reconstituer(
            PaiementId id,
            InscriptionId inscriptionId,
            LocalDate datePaiement,
            Montant montant,
            Montant montantPrevu,
            TypePaiement typePaiement,
            CanalPaiement canalPaiement,
            MoisAcademique mois,
            StatutPaiement statut
    ) {
        Paiement paiement = new Paiement(
                id,
                inscriptionId,
                datePaiement,
                montant,
                typePaiement,
                canalPaiement,
                mois,
                montantPrevu,
                statut
        );
        return paiement;
    }

    private void verifierCoherence() {
        if (typePaiement == TypePaiement.MENSUALITE && moisAcademique == null) {
            throw new ScolariteException("Paiement mensuel sans période");
        }
    }

    /* =======================
       CYCLE DE VIE
       ======================= */



    private void verifierStatut(StatutPaiement attendu) {
        if (this.statut != attendu) {
            throw new IllegalStateException(
                    "Transition invalide : " + statut + " → " + attendu
            );
        }
    }

    public void attribuerStatut(StatutPaiement statut){
        this.statut = statut;
    }

    /* =======================
       GETTERS
       ======================= */

    @Override
    public PaiementId getId() {
        return id;
    }

    public InscriptionId getInscriptionId() {
        return inscriptionId;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public Montant getMontant() {
        return montant;
    }

    public TypePaiement getTypePaiement() {
        return typePaiement;
    }

    public StatutPaiement getStatut() {
        return statut;
    }

    public MoisAcademique getMoisAcademique() {
        return moisAcademique;
    }

    public Montant getMontantPrevu() {
        return montantPrevu;
    }

    public void modifierMontantPrevu(Montant nouveauMontant) {
        if (nouveauMontant == null) {
            throw new IllegalArgumentException("Montant prévu invalide");
        }

        this.montantPrevu = nouveauMontant;
    }

    public CanalPaiement getCanalPaiement() {
        return canalPaiement;
    }
}
