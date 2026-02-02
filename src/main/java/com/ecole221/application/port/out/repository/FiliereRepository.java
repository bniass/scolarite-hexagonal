package com.ecole221.application.port.out.repository;

import com.ecole221.domain.entity.school.CodeFiliere;
import com.ecole221.domain.entity.school.Filiere;

import java.util.Optional;

public interface FiliereRepository {
    boolean existsByCode(CodeFiliere code);
    void save(Filiere filiere);
    Optional<Filiere> findByCode(CodeFiliere code);
}
