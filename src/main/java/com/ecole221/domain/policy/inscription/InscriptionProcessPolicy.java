package com.ecole221.domain.policy.inscription;


import com.ecole221.domain.entity.academic.AnneeAcademique;
import org.springframework.stereotype.Component;

@Component
public class InscriptionProcessPolicy {
    public void verifierInscriptionAutorisee(AnneeAcademique annee) {
        annee.verifierInscriptionsOuvertes();
    }
}

