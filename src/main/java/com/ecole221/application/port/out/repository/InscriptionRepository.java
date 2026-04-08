package com.ecole221.application.port.out.repository;

import com.ecole221.domain.entity.inscription.Inscription;
import com.ecole221.domain.entity.inscription.InscriptionId;
import com.ecole221.infrastructure.persistence.inscription.entity.InscriptionJpaEntity;

import java.util.List;
import java.util.Optional;

public interface InscriptionRepository {
    void save(Inscription inscription);
    Optional<Inscription> findByMatriculeAndAnnee(String matricule, String anneeAcademic);
    boolean existsByMatriculeAndAnnee(String matricule, String anneeAcademic);
    long count();
    List<Inscription> all();

    void delete(Inscription inscription);
}
