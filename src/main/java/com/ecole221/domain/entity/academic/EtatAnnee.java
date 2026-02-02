package com.ecole221.domain.entity.academic;

public interface EtatAnnee {
    void creer(AnneeAcademique annee, DatesAnnee dates);
    void modifier(AnneeAcademique annee, DatesAnnee dates);
    void publier(AnneeAcademique annee);
    void ouvrirInscriptions(AnneeAcademique annee);
    void fermerInscriptions(AnneeAcademique annee);
    void cloturer(AnneeAcademique annee);
    void suspendreInscriptions(AnneeAcademique annee);
    void verifierInscriptionsOuvertes();
}

