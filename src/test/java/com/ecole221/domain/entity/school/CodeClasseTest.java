package com.ecole221.domain.entity.school;


import com.ecole221.domain.entity.school.CodeClasse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CodeClasseTest {
    @Test
    public void deux_codes_classes_egaux_sont_identiques_metier() {
        CodeClasse c1 = new CodeClasse("L3-INFO-A");
        CodeClasse c2 = new CodeClasse("L3-INFO-A");

        assertEquals(c1, c2);
        assertEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    public void codes_classes_differents_ne_sont_pas_egaux() {
        CodeClasse c1 = new CodeClasse("L3-INFO-A");
        CodeClasse c2 = new CodeClasse("L3-INFO-B");

        assertNotEquals(c1, c2);
    }

    @Test
    public void code_classe_invalide_est_refuse() {
        assertThrows(IllegalArgumentException.class,
                () -> new CodeClasse(""));
    }
}
