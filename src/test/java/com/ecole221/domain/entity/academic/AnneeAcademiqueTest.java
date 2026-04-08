package com.ecole221.domain.entity.academic;

import com.ecole221.domain.event.anneeacademic.AnneeAcademiqueCreeeEvent;
import com.ecole221.domain.exception.ScolariteException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class AnneeAcademiqueTest {

    @Test
    void creation_reussie_emet_evenement_creee_et_place_en_brouillon() {
        AnneeAcademique a = new AnneeAcademique(new AnneeAcademiqueId(2025));

        a.creer(datesValides());

        var events = a.pullDomainEvents();
        assertTrue(events.stream().anyMatch(e -> e instanceof AnneeAcademiqueCreeeEvent));

        // brouillon → publication autorisée
        assertDoesNotThrow(a::publier);
    }

    @Test
    void modification_est_autorisee_en_brouillon() {
        AnneeAcademique a = new AnneeAcademique(new AnneeAcademiqueId(2025));
        a.creer(datesValides());

        assertDoesNotThrow(() -> a.modifier(datesValides()));
    }

    @Test
    void modification_est_interdite_apres_publication() {
        AnneeAcademique a = new AnneeAcademique(new AnneeAcademiqueId(2025));
        a.creer(datesValides());
        a.publier();

        ScolariteException e = assertThrows(ScolariteException.class, () -> a.modifier(datesValides()));
        System.out.println(e.getMessage());
    }

    @Test
    void suspendre_et_reouvrir_inscriptions() {
        AnneeAcademique a = new AnneeAcademique(new AnneeAcademiqueId(2025));
        a.creer(datesValides());
        a.publier();
        a.ouvrirInscriptions();
        a.suspendreInscriptions(); // OK
        assertThrows(ScolariteException.class, a::suspendreInscriptions);
        a.ouvrirInscriptions(); // réouverture
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
