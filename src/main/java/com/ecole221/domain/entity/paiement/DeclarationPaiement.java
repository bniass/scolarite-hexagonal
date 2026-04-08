package com.ecole221.domain.entity.paiement;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class DeclarationPaiement {

    private final TypePaiement typePaiement;
    private final String reference;
    private final double montant;
    private final LocalDate datePaiement;
    private final LocalTime heurePaiement;
    private final String preuvePaiement; // chemin / id fichier

    public DeclarationPaiement(
            TypePaiement typePaiement,
            String reference,
            double montant,
            LocalDate datePaiement,
            LocalTime heurePaiement,
            String preuvePaiement
    ) {
        this.typePaiement = Objects.requireNonNull(typePaiement);
        this.reference = Objects.requireNonNull(reference);
        this.montant = montant;
        this.datePaiement = Objects.requireNonNull(datePaiement);
        this.heurePaiement = Objects.requireNonNull(heurePaiement);
        this.preuvePaiement = Objects.requireNonNull(preuvePaiement);

        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant doit être positif");
        }
    }

    public TypePaiement getTypePaiement() {
        return typePaiement;
    }

    public String getReference() {
        return reference;
    }

    public double getMontant() {
        return montant;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public LocalTime getHeurePaiement() {
        return heurePaiement;
    }

    public String getPreuvePaiement() {
        return preuvePaiement;
    }
}

