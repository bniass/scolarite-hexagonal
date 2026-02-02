package com.ecole221.domain.entity.inscription;

import com.ecole221.domain.exception.ScolariteException;
import com.ecole221.domain.exception.TransfertNonAutoriseException;
import com.ecole221.domain.entity.school.Classe;
import com.ecole221.domain.policy.RegleTransfert;
import com.ecole221.domain.shared.AggregateRoot;

import java.time.LocalDate;
import java.util.Objects;

public class Inscription implements AggregateRoot<InscriptionId> {
    private final InscriptionId id;
    private Classe classe;
    private final LocalDate dateInscription;

    public Inscription(InscriptionId id, Classe classe, LocalDate dateInscription) {
        this.id = id;
        this.classe = classe;
        this.dateInscription = dateInscription;
    }

    public Inscription creerNouvelle(InscriptionId id, Classe classe) {
        return new Inscription(id, classe, LocalDate.now());
    }

    @Override
    public InscriptionId getId() {
        return id;
    }

    public void transfererVers(Classe nouvelleClasse, LocalDate dateTransfert, RegleTransfert regleTransfert) {
        Objects.requireNonNull(nouvelleClasse);
        Objects.requireNonNull(regleTransfert);

        if (nouvelleClasse.equals(this.classe)) {
            throw new ScolariteException(
                    "La classe de destination est identique à la classe courante"
            );
        }

        if (!regleTransfert.estAutorise(
                this.dateInscription,
                dateTransfert,
                this.classe,
                nouvelleClasse
        )) {
            throw new TransfertNonAutoriseException(
                    id.getMatricule().value(),
                    id.getAnneeAcademique().toString()
            );
        }

        this.classe = nouvelleClasse;
    }

    public Classe classeCourante() {
        return classe;
    }

    public boolean estDansLaClasse(Classe classe) {
        return this.classe.equals(classe);
    }

    public boolean transfertPossible(LocalDate aujourdHui) {
        return !dateInscription.plusMonths(1).isBefore(aujourdHui);
    }

    public void transfererVers(Classe nouvelleClasse, LocalDate aujourdHui) {
        if (!transfertPossible(aujourdHui)) {
            throw new TransfertNonAutoriseException(
                    id.getMatricule().value(),
                    id.getAnneeAcademique().toString()
            );
        }
        this.classe = nouvelleClasse;
    }

    public LocalDate getDateInscription() {
        return dateInscription;
    }
}
