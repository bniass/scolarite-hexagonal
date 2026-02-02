package com.ecole221.domain.entity.academic;

import com.ecole221.domain.event.anneeacademic.AnneeAcademiqueClotureeEvent;
import com.ecole221.domain.event.anneeacademic.InscriptionsOuvertesEvent;
import com.ecole221.domain.exception.ScolariteException;

public class InscriptionsSuspendues extends AbstractEtatAnnee {

    @Override
    public void ouvrirInscriptions(AnneeAcademique annee) {
        annee.changerEtat(new InscriptionsOuvertes());
        annee.addEvent(new InscriptionsOuvertesEvent(annee, annee.getDateOuvertureInscription(), annee.getDateFinInscription()));
    }

    @Override
    public void cloturer(AnneeAcademique annee) {
        annee.changerEtat(new AnneeCloturee());
        annee.addEvent(new AnneeAcademiqueClotureeEvent(annee));
    }

    @Override
    public void fermerInscriptions(AnneeAcademique annee) {

    }
}

