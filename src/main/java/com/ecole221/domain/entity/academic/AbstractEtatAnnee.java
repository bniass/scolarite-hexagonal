package com.ecole221.domain.entity.academic;

import com.ecole221.domain.exception.ScolariteException;

public abstract class AbstractEtatAnnee implements EtatAnnee {

    protected void interdit(String action) {
        throw new ScolariteException(
                "Action interdite : " + action + " dans l'état " + getClass().getSimpleName()
        );
    }

    @Override
    public void creer(AnneeAcademique annee, DatesAnnee dates) {
        interdit("créer classe");
    }

    @Override
    public void modifier(AnneeAcademique annee, DatesAnnee dates) {
        interdit("modifier dates");
    }

    @Override
    public void publier(AnneeAcademique annee) {
        interdit("publier");
    }

    @Override
    public void ouvrirInscriptions(AnneeAcademique annee) {
        interdit("ouvrir inscriptions");
    }

    @Override
    public void cloturer(AnneeAcademique annee) {
        interdit("clôturer");
    }

    @Override
    public void suspendreInscriptions(AnneeAcademique annee) {
        interdit("suspendre inscriptions");
    }

    @Override
    public void verifierInscriptionsOuvertes() {
        throw new ScolariteException("Les inscriptions ne sont pas ouvertes");
    }

    @Override
    public void fermerInscriptions(AnneeAcademique annee) {
        interdit("fermer inscriptions");
    }
}

