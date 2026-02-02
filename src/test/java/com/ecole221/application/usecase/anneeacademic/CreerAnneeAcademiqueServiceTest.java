package com.ecole221.application.usecase.anneeacademic;

import com.ecole221.ScolariteHexagonalApplication;
import com.ecole221.application.command.anneeacademique.CreerAnneeAcademiqueCommand;
import com.ecole221.application.port.in.anneeacademique.CreerAnneeAcademiqueUseCase;
import com.ecole221.application.port.out.repository.AnneeAcademiqueRepository;
import com.ecole221.domain.event.anneeacademic.AnneeAcademiqueCreeeEvent;
import com.ecole221.domain.exception.AnneeAcadiqueException;
import com.ecole221.infrastructure.event.TestDomainEventPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(
        classes = ScolariteHexagonalApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@ActiveProfiles("test")
class CreerAnneeAcademiqueServiceTest {

    @Autowired
    private CreerAnneeAcademiqueUseCase useCase;

    @Autowired
    private TestDomainEventPublisher eventPublisher;

    @Autowired
    private AnneeAcademiqueRepository repository;

    @BeforeEach
    void setUp() {
        eventPublisher.clear();
    }

    @Test
    void creation_annee_academique_valide_publie_evenement() {

        // GIVEN
        CreerAnneeAcademiqueCommand command =
                new CreerAnneeAcademiqueCommand(
                        2025,
                        LocalDate.of(2025, 9, 1),
                        LocalDate.of(2026, 5, 31),
                        LocalDate.of(2025, 6, 1),
                        LocalDate.of(2025, 11, 30)
                );

        // WHEN
        useCase.executer(command);

        // THEN
        assertThat(eventPublisher.events())
                .hasSize(1)
                .first()
                .isInstanceOf(AnneeAcademiqueCreeeEvent.class);
    }

    @Test
    void creation_annee_academique_date_fin_annee_invalides() {

        // GIVEN
        CreerAnneeAcademiqueCommand command =
                new CreerAnneeAcademiqueCommand(
                        2025,
                        LocalDate.of(2025, 9, 1),
                        LocalDate.of(2024, 5, 31), //date fin
                        LocalDate.of(2025, 6, 1),
                        LocalDate.of(2025, 11, 30)
                );

        // WHEN
        AnneeAcadiqueException exception = assertThrows(AnneeAcadiqueException.class,
                () -> useCase.executer(command));

        assertThat(exception.getMessage())
                .isEqualTo("La date de début de l'année doit être antérieure à la date de fin de la l'année académique");

    }

    @Test
    void creation_annee_academique_existante_leve_exception() {

        // GIVEN
        CreerAnneeAcademiqueCommand command =
                new CreerAnneeAcademiqueCommand(
                        2025,
                        LocalDate.of(2025, 9, 1),
                        LocalDate.of(2026, 5, 31),
                        LocalDate.of(2025, 6, 1),
                        LocalDate.of(2025, 11, 30)
                );

        // Première création (valide)
        useCase.executer(command);

        // WHEN
        AnneeAcadiqueException exception =
                assertThrows(
                        AnneeAcadiqueException.class,
                        () -> useCase.executer(command)
                );

        // THEN
        assertThat(exception.getMessage())
                .isEqualTo("Année académique déjà existante");
    }

}

