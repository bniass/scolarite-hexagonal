package com.ecole221.infrastructure.persistence.classe.repository;

import com.ecole221.infrastructure.persistence.classe.entity.ClasseJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClasseJpaRepository extends JpaRepository<ClasseJpaEntity, Long> {
    boolean existsByCode(String code);

    boolean existsByNom(String nom);

    Optional<ClasseJpaEntity> findByCode(String code);
}
