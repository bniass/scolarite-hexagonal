package com.ecole221.domain.entity.school;


import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class ClasseTest {

    @Test
    public void classe_expose_son_identite_metier() {
        Classe classe = ClasseTestFactory.creerClasse();

        CodeClasse id = classe.getId();

        assertNotNull(id);
        assertEquals(new CodeClasse("L3-INFO-A"), id);
    }

    @Test
    public void montant_total_inscription_est_calcule_selon_la_regle_metier() {
        Classe classe = ClasseTestFactory.creerClasse();

        Montant total = classe.montantTotalInscription();

        assertEquals(
                new BigDecimal("90000"),
                total.valeur()
        );
    }
}
