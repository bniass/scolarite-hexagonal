package com.ecole221.infrastructure.projection.inscription.repository;

import com.ecole221.infrastructure.projection.inscription.entity.AnneeAcademiqueProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnneeAcademiqueProjectionRepository
        extends JpaRepository<AnneeAcademiqueProjection, String> {

    boolean existsByAnneeAcademiqueCodeAndInscriptionsOuvertesTrue(String anneeAcademiqueCode);
}
