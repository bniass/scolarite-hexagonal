package com.ecole221.infrastructure.anneeacademique;

import com.ecole221.ScolariteHexagonalApplication;
import com.ecole221.application.port.out.repository.AnneeAcademiqueRepository;
import com.ecole221.domain.entity.academic.AnneeAcademique;
import com.ecole221.domain.entity.academic.DatesAnnee;
import com.ecole221.domain.entity.academic.Statut;
import com.ecole221.infrastructure.persistence.anneeacademique.mapper.EtatAnneeFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(
        classes = ScolariteHexagonalApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = "spring.profiles.active=inmemory")
@Testcontainers
public class AnneeAcademiqueRepositoryMySqlTest {

    @Container
    static MySQLContainer<?> mysql =
            new MySQLContainer<>("mysql:8.0")
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test");

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");

        System.out.println(mysql.getJdbcUrl());
        System.out.println(mysql.getUsername());
        System.out.println(mysql.getPassword());
    }

    @Autowired
    private AnneeAcademiqueRepository repository;

    @Test
    void save_and_find_by_code() {
        // ===== GIVEN =====
        AnneeAcademique annee = AnneeAcademique.reconstituer(
                "2024-2025",
                new DatesAnnee(
                        LocalDate.of(2024, 10, 1),
                LocalDate.of(2025, 6, 30),
                LocalDate.of(2024, 8, 1),
                LocalDate.of(2024, 11, 30)
                ),
                LocalDate.of(2024, 7, 15),
                EtatAnneeFactory.fromStatut(Statut.valueOf("BROUILLON")),
                null
        );

        // ===== WHEN =====
        repository.save(annee);

        Optional<AnneeAcademique> result =
                repository.findByCode("2024-2025");

        // ===== THEN =====
        assertThat(result).isPresent();

        AnneeAcademique loaded = result.get();

        assertThat(loaded.getId().value()).isEqualTo("2024-2025");
        assertThat(loaded.getDateDebut())
                .isEqualTo(LocalDate.of(2024, 10, 1));
        assertThat(loaded.getDateFin())
                .isEqualTo(LocalDate.of(2025, 6, 30));
        assertThat(EtatAnneeFactory.toStatut(loaded.getEtat()))
                .isEqualTo(Statut.BROUILLON);
    }
}
