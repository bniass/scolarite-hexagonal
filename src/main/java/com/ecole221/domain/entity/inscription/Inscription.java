package com.ecole221.domain.entity.inscription;

import com.ecole221.domain.entity.school.CodeClasse;
import com.ecole221.domain.event.inscription.InscriptionConfirmeeEvent;
import com.ecole221.domain.event.inscription.InscriptionCreeeEvent;
import com.ecole221.domain.event.inscription.InscriptionInvalideeEvent;
import com.ecole221.domain.event.shared.EventAggregateRoot;
import com.ecole221.domain.exception.ScolariteException;
import com.ecole221.domain.exception.TransfertNonAutoriseException;
import com.ecole221.domain.policy.inscription.RegleTransfert;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;

public class Inscription extends EventAggregateRoot {

    private static final int DELAI_PAIEMENT_JOURS = 14;

    private final InscriptionId id;
    private CodeClasse codeClasse;
    private final LocalDate dateInscription;
    private EtatInscription etat;
    private StatutInscription statutInscription;


    /* =======================
       CONSTRUCTEUR / FACTORY
       ======================= */

    private Inscription(InscriptionId id, CodeClasse codeClasse, LocalDate dateInscription) {
        this.id = Objects.requireNonNull(id);
        this.codeClasse = Objects.requireNonNull(codeClasse);
        this.dateInscription = Objects.requireNonNull(dateInscription);
        this.etat = EtatInscription.EN_ATTENTE_PAIEMENT;
    }

    public static Inscription creerNouvelle(InscriptionCreationSnapshot snapshot) {
        Inscription inscription = new Inscription(snapshot.inscriptionId(), snapshot.codeClasse(), LocalDate.now());
        //doit déclencher le context Paiement qui initialise les futurs paiements de l'étudiant
        //12 lignes créer dans la table inscription (Frais inscription, autres frais et 9 mois avec le statut impayé
        inscription.addEvent(new InscriptionCreeeEvent(snapshot));
        return inscription;
    }

    /* =======================
       COMPORTEMENTS METIER
       ======================= */

    public void confirmerInscription() {
        verifierEtat(EtatInscription.PAYEE);
        this.etat = EtatInscription.CONFIRMEE;
        addEvent(new InscriptionConfirmeeEvent(this.id));
    }

    public void invalider(String motif) {
        this.etat = EtatInscription.INVALIDEE;
        addEvent(new InscriptionInvalideeEvent(this.id, motif));
    }

    /* =======================
       TRANSFERT DE CLASSE
       ======================= */

    public void transfererVers(
            CodeClasse nouvelleClasse,
            LocalDate dateTransfert,
            RegleTransfert regleTransfert
    ) {
        verifierEtat(EtatInscription.CONFIRMEE);

        Objects.requireNonNull(nouvelleClasse);
        Objects.requireNonNull(regleTransfert);

        if (nouvelleClasse.equals(this.codeClasse)) {
            throw new ScolariteException(
                    "La classe de destination est identique à la classe courante"
            );
        }

        if (!regleTransfert.estAutorise(
                this.dateInscription,
                dateTransfert,
                this.codeClasse,
                nouvelleClasse
        )) {
            throw new TransfertNonAutoriseException(
                    id.getMatricule().value(),
                    id.getAnneeAcademiqueId().value()
            );
        }

        this.codeClasse = nouvelleClasse;
    }

    /* =======================
       REGLES INTERNES
       ======================= */

    public void verifierEtat(EtatInscription... etatsAutorises) {
        for (EtatInscription etatAutorise : etatsAutorises) {
            if (this.etat == etatAutorise) {
                return;
            }
        }
        throw new ScolariteException(
                "Action interdite dans l'état " + this.etat
        );
    }

    private boolean paiementHorsDelai() {
        long jours = ChronoUnit.DAYS.between(
                dateInscription,
                LocalDate.now()
        );
        return jours > DELAI_PAIEMENT_JOURS;
    }

    /* =======================
       GETTERS
       ======================= */

    public InscriptionId getId() {
        return id;
    }

    public CodeClasse getCodeClasse() {
        return codeClasse;
    }

    public LocalDate getDateInscription() {
        return dateInscription;
    }

    public EtatInscription getEtat() {
        return etat;
    }

}
