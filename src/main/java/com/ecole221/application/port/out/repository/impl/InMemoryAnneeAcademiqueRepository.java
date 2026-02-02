package com.ecole221.application.port.out.repository.impl;

import com.ecole221.application.port.out.repository.AnneeAcademiqueRepository;
import com.ecole221.domain.entity.academic.AnneeAcademique;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@Profile({"inmemory", "test"})
public class InMemoryAnneeAcademiqueRepository implements AnneeAcademiqueRepository {

    private final Map<String, AnneeAcademique> data = new HashMap<>();

    @Override
    public Optional<AnneeAcademique> findByCode(String code) {
        return Optional.ofNullable(data.get(code));
    }

    @Override
    public void save(AnneeAcademique anneeAcademique) {
        data.put(anneeAcademique.getId().value(), anneeAcademique);
    }

}
