package com.ecole221.infrastructure.persistence.anneeacademique.repository;

import com.ecole221.infrastructure.persistence.anneeacademique.entity.AnneeMoisJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnneeMoisJpaRepository extends JpaRepository<AnneeMoisJpaEntity, Long> {
    Optional<AnneeMoisJpaEntity> findByAnneeAcademiqueCodeAndMoisAndAnnee(
            String anneeAcademiqueCode,
            int mois,
            int annee
    );

}
