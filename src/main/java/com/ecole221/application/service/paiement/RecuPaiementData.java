package com.ecole221.application.service.paiement;

import com.ecole221.domain.entity.paiement.Paiement;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public record RecuPaiementData(
        String matricule,
        String nom,
        String prenom,
        String classe,
        String datePaiement,
        List<Paiement> moisPayes,
        int totalPaye
) {
    public String getMoisLibelle(int m, int annee) {

        String mois = Month.of(m)
                .getDisplayName(TextStyle.FULL, Locale.FRENCH);

        mois = mois.substring(0,1).toUpperCase() + mois.substring(1);

        return mois + " " + annee;
    }
}
