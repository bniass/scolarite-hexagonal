package com.ecole221.infrastructure.persistence.anneeacademique.repository;

import com.ecole221.infrastructure.persistence.anneeacademique.entity.AnneeAcademiqueJpaEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnneeAcademiqueJpaRepository
        extends JpaRepository<AnneeAcademiqueJpaEntity, Long> {
    @Query("""
            select a from AnneeAcademiqueJpaEntity a
            left join fetch a.moisAcademiques
            where a.code = :code
            """)
    Optional<AnneeAcademiqueJpaEntity> findByCode(String code);
}

