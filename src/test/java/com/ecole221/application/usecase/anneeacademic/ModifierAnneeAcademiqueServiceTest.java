package com.ecole221.application.usecase.anneeacademic;

import com.ecole221.ScolariteHexagonalApplication;
import com.ecole221.application.command.anneeacademique.CreerAnneeAcademiqueCommand;
import com.ecole221.application.command.anneeacademique.ModifierAnneeAcademiqueCommand;
import com.ecole221.application.port.in.anneeacademique.CreerAnneeAcademiqueUseCase;
import com.ecole221.application.port.in.anneeacademique.ModifierAnneeAcademiqueUseCase;
import com.ecole221.application.port.out.repository.AnneeAcademiqueRepository;
import com.ecole221.domain.entity.academic.AnneeAcademique;
import com.ecole221.domain.exception.AnneeAcadiqueException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(
        classes = ScolariteHexagonalApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@ActiveProfiles("test")
class ModifierAnneeAcademiqueServiceTest {

    @Autowired
    private CreerAnneeAcademiqueUseCase creerUseCase;

    @Autowired
    private ModifierAnneeAcademiqueUseCase modifierUseCase;

    @Autowired
    private AnneeAcademiqueRepository repository;

    @Test
    void modification_annee_en_brouillon_est_autorisee() {

        // GIVEN — une année académique créée (statut BROUILLON)
        CreerAnneeAcademiqueCommand creation =
                AnneeAcademiqueTestFactory.creerAnneeAcademique();

        creerUseCase.executer(creation);

        ModifierAnneeAcademiqueCommand modification =
                AnneeAcademiqueTestFactory.modifierAnneeAcademique();

        // WHEN
        modifierUseCase.executer(modification);

        // THEN — l’année est bien modifiée
        AnneeAcademique annee =
                repository.findByCode("2025-2026").orElseThrow();

        assertThat(annee.getDateDebut())
                .isEqualTo(LocalDate.of(2025, 10, 1));

        assertThat(annee.getDateFin())
                .isEqualTo(LocalDate.of(2026, 6, 30));

        assertThat(annee.moisAcademiques())
                .hasSize(9); // invariant métier
    }

    @Test
    void modification_annee_en_deja_publie() {
        // GIVEN — une année académique créée (statut BROUILLON)
        CreerAnneeAcademiqueCommand creation =
                AnneeAcademiqueTestFactory.creerAnneeAcademique();

        // WHEN
        creerUseCase.executer(creation);

        AnneeAcademique annee =
                repository.findByCode("2025-2026").orElseThrow();

       annee.publier();

       ModifierAnneeAcademiqueCommand modification = AnneeAcademiqueTestFactory
               .modifierAnneeAcademique();

        // WHEN
        AnneeAcadiqueException exception =
                assertThrows(
                        AnneeAcadiqueException.class,
                        () -> modifierUseCase.executer(modification)
                );

        assertThat(exception.getMessage())
                .isEqualTo("Action interdite (modification), statut actuel = PUBLIEE");


    }
}
