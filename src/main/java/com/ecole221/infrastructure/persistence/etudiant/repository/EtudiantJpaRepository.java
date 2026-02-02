package com.ecole221.infrastructure.persistence.etudiant.repository;

import com.ecole221.infrastructure.persistence.etudiant.entity.EtudiantJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EtudiantJpaRepository extends JpaRepository<EtudiantJpaEntity, Long> {
    EtudiantJpaEntity findByMatricule(String matricule);
    boolean existsByMatricule(String matricule);
}
