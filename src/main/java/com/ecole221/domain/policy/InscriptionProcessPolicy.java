package com.ecole221.domain.policy;


import com.ecole221.domain.entity.academic.AnneeAcademique;
import com.ecole221.domain.entity.inscription.Inscription;

public class InscriptionProcessPolicy {

    public void verifierEtCreerInscription(AnneeAcademique annee) {
        // règle cross-aggregate
        annee.verifierInscriptionsOuvertes();

        //inscription.creerNouvelle(inscription.getId(), inscription.classeCourante()); // règles propres à Inscription
    }
}

