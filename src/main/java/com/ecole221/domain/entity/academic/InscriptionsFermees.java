package com.ecole221.domain.entity.academic;

import com.ecole221.domain.event.anneeacademic.AnneeAcademiqueClotureeEvent;
import com.ecole221.domain.event.anneeacademic.InscriptionsOuvertesEvent;

public class InscriptionsFermees extends AbstractEtatAnnee{
    @Override
    public void ouvrirInscriptions(AnneeAcademique annee) {
        annee.changerEtat(new InscriptionsOuvertes());
    }

    @Override
    public void cloturer(AnneeAcademique annee) {
        annee.changerEtat(new AnneeCloturee());
    }

}
