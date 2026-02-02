package com.ecole221.application.port.out.repository;

import com.ecole221.domain.entity.academic.AnneeAcademique;

import java.util.Optional;


public interface AnneeAcademiqueRepository {
    Optional<AnneeAcademique> findByCode(String code);

    void save(AnneeAcademique anneeAcademique);
}
