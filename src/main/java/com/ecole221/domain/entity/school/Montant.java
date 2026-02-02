package com.ecole221.domain.entity.school;

import java.math.BigDecimal;

public final class Montant {
    private final BigDecimal valeur;

    public Montant(BigDecimal valeur) {
        if (valeur.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "Le montant ne peut pas être négatif"
            );
        }
        this.valeur = valeur;
    }

    public Montant additionner(Montant autre) {
        return new Montant(this.valeur.add(autre.valeur));
    }

    public BigDecimal valeur() {
        return valeur;
    }
}
