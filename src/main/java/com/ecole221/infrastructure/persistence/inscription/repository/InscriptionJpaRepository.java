package com.ecole221.infrastructure.persistence.inscription.repository;

import com.ecole221.infrastructure.persistence.inscription.entity.InscriptionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InscriptionJpaRepository extends JpaRepository<InscriptionJpaEntity, Long> {
    Optional<InscriptionJpaEntity>
    findByEtudiant_MatriculeAndAnneeAcademique_Code(
            String matricule,
            String codeAnnee
    );

    boolean
    existsByEtudiant_MatriculeAndAnneeAcademique_Code(
            String matricule,
            String codeAnnee
    );
}
