package com.ecole221.domain.entity.school;


import com.ecole221.domain.entity.school.NomClasse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NomClasseTest {

    @Test
    public void nom_classe_valide_est_cree() {
        NomClasse nom = new NomClasse("Licence 3 Informatique A");

        assertEquals("Licence 3 Informatique A", nom.value());
    }

    @Test
    public void nom_classe_vide_est_refuse() {
        assertThrows(IllegalArgumentException.class,
                () -> new NomClasse(""));
    }

    @Test
    public void nom_classe_null_est_refuse() {
        assertThrows(IllegalArgumentException.class,
                () -> new NomClasse(null));
    }

    @Test
    public void deux_noms_classes_identiques_sont_egaux() {
        NomClasse n1 = new NomClasse("Licence 3 Informatique A");
        NomClasse n2 = new NomClasse("Licence 3 Informatique A");

        assertEquals(n1, n2);
        assertEquals(n1.hashCode(), n2.hashCode());
    }

    @Test
    public void deux_noms_classes_differents_ne_sont_pas_egaux() {
        NomClasse n1 = new NomClasse("Licence 3 Informatique A");
        NomClasse n2 = new NomClasse("Licence 3 Informatique B");

        assertNotEquals(n1, n2);
    }
}
