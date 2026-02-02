package com.ecole221.domain.entity.school;

import com.ecole221.domain.entity.school.*;

import java.math.BigDecimal;

public class ClasseTestFactory {

    public static Classe creerClasse() {
        return new Classe(
                new CodeClasse("L3-INFO-A"),
                new NomClasse("Licence 3 Informatique A"),
                creerFiliere(),
                new Montant(new BigDecimal("20000")),
                new Montant(new BigDecimal("60000")),
                new Montant(new BigDecimal("10000"))
        );
    }

    public static Classe autreClasse() {
        return new Classe(
                new CodeClasse("L3-INFO-B"),
                new NomClasse("Licence 3 Informatique B"),
                creerFiliere(),
                new Montant(new BigDecimal("20000")),
                new Montant(new BigDecimal("60000")),
                new Montant(new BigDecimal("10000"))
        );
    }

    private static Filiere creerFiliere() {
        return new Filiere(
                new CodeFiliere("INFO"),
                new NomFiliere("Informatique")
        );
    }
}

