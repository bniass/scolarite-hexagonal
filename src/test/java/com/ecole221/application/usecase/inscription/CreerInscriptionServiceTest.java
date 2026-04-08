package com.ecole221.application.usecase.inscription;

import com.ecole221.application.command.iscription.CreerInscriptionCommand;
import com.ecole221.application.port.in.inscription.CreerInscriptionUseCase;
import com.ecole221.application.port.out.repository.impl.InMemoryAnneeAcademiqueRepository;
import com.ecole221.application.port.out.repository.impl.InMemoryClasseRepository;
import com.ecole221.application.port.out.repository.impl.InMemoryInscriptionRepository;
import com.ecole221.domain.entity.academic.AnneeAcademique;
import com.ecole221.domain.entity.academic.AnneeAcademiqueId;
import com.ecole221.domain.entity.academic.DatesAnnee;
import com.ecole221.domain.entity.school.*;
import com.ecole221.domain.exception.ScolariteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreerInscriptionServiceTest {

    private CreerInscriptionUseCase useCase;
    private InMemoryAnneeAcademiqueRepository anneeRepo;
    private InMemoryClasseRepository classeRepo;
    private InMemoryInscriptionRepository inscriptionRepo;
/*
    @BeforeEach
    void setup() {
        anneeRepo = new InMemoryAnneeAcademiqueRepository();
        classeRepo = new InMemoryClasseRepository();
        inscriptionRepo = new InMemoryInscriptionRepository();

        useCase = new CreerInscriptionService(
                classeRepo,
                inscriptionRepo,
                anneeRepo
        );
    }

    @Test
    void creer_inscription_quand_inscriptions_ouvertes() {

        // GIVEN
        AnneeAcademique annee = creerAnneeOuverte();
        anneeRepo.save(annee);

        Classe classe = new Classe(
                new CodeClasse("L3-INFO-A"),
                new NomClasse("Licence 3 Informatique A"),
                new Filiere(new CodeFiliere("INFO"), new NomFiliere("Informatique")),
                new Montant(new BigDecimal("20000")),
                new Montant(new BigDecimal("60000")),
                new Montant(new BigDecimal("10000"))
        );
        classeRepo.save(classe);

        CreerInscriptionCommand cmd =
                new CreerInscriptionCommand(
                        "L3-INFO-A", "MAT-001", annee.getId().value()
                );

        // WHEN
        useCase.executer(cmd);

        // THEN
        assertEquals(1, inscriptionRepo.count());
    }

    @Test
    void refuser_inscription_si_inscriptions_fermees() {

        AnneeAcademique annee = creerAnneePublieeMaisNonOuverte();
        anneeRepo.save(annee);

        Classe classe = new Classe(
                new CodeClasse("L3-INFO-A"),
                new NomClasse("Licence 3 Informatique A"),
                new Filiere(new CodeFiliere("INFO"), new NomFiliere("Informatique")),
                new Montant(new BigDecimal("20000")),
                new Montant(new BigDecimal("60000")),
                new Montant(new BigDecimal("10000"))
        );
        classeRepo.save(classe);

        CreerInscriptionCommand cmd =
                new CreerInscriptionCommand("L3-INFO-A", "MAT-001", annee.getId().value());

        ScolariteException ex = assertThrows(ScolariteException.class,
                () -> useCase.executer(cmd)
        );
        System.out.println(ex);
    }

 */

    // ============================
    // HELPERS
    // ============================

    private AnneeAcademique creerAnneeOuverte() {
        AnneeAcademique a = new AnneeAcademique(new AnneeAcademiqueId(2025));

        a.creer(
                new DatesAnnee(
                        LocalDate.of(2025, 10, 1),
                        LocalDate.of(2026, 6, 30),
                        LocalDate.of(2025, 9, 1),
                        LocalDate.of(2025, 12, 1)
                )

        );
        a.publier();
        a.ouvrirInscriptions();
        return a;
    }

    private AnneeAcademique creerAnneePublieeMaisNonOuverte() {
        AnneeAcademique a = new AnneeAcademique(new AnneeAcademiqueId(2025));
        a.creer(new DatesAnnee(
                LocalDate.of(2025, 10, 1),
                LocalDate.of(2026, 6, 30),
                LocalDate.of(2025, 9, 1),
                LocalDate.of(2025, 12, 1)
        ));
        a.publier();
        return a;
    }
}

