package com.ecole221.domain.entity.inscription;

import com.ecole221.domain.entity.academic.AnneeAcademique;
import com.ecole221.domain.entity.academic.AnneeAcademiqueId;
import com.ecole221.domain.entity.academic.DatesAnnee;
import com.ecole221.domain.entity.school.*;
import com.ecole221.domain.entity.student.Matricule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
public class InscriptionTest {

    @Test
    void creerInscriptionTest(){
        AnneeAcademique a = new AnneeAcademique(new AnneeAcademiqueId(2025, 2026));
        a.creer(datesValides());
        a.publier();
        a.ouvrirInscriptions();

        InscriptionId inscriptionId = new InscriptionId(new Matricule("M202500001"), a);
        Inscription inscription =
               new Inscription(inscriptionId,
                       new Classe(
                               new CodeClasse("L1GL"),
                               new NomClasse("Licence 1 Génie Logiciel"),
                               new Filiere(
                                       new CodeFiliere("GL"),
                                       new NomFiliere("Génie Logiciel")
                               ),
                               new Montant(new BigDecimal(100000)),
                               new Montant(new BigDecimal(80000)),
                               new Montant(new BigDecimal(75000))
                       ),
                       LocalDate.of(2025, 10, 1)
               );
        System.out.println(inscription.getId().value());
        Assertions.assertEquals(inscription.getId().value(), "M202500001_2025-2026");
    }

    private DatesAnnee datesValides() {
        return new DatesAnnee(
                LocalDate.of(2025, 10, 1),
                LocalDate.of(2026, 6, 30),
                LocalDate.of(2025, 9, 1),
                LocalDate.of(2025, 10, 15)
        );
    }
}
