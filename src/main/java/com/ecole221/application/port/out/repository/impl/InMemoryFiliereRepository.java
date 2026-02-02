package com.ecole221.application.port.out.repository.impl;

import com.ecole221.application.port.out.repository.FiliereRepository;
import com.ecole221.domain.entity.school.CodeFiliere;
import com.ecole221.domain.entity.school.Filiere;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryFiliereRepository implements FiliereRepository {
    private final Map<CodeFiliere, Filiere> data = new HashMap<>();

    @Override
    public boolean existsByCode(CodeFiliere code) {
        return data.containsKey(code);
    }

    @Override
    public void save(Filiere filiere) {
        data.put(filiere.getCodeFiliere(), filiere);
    }

    @Override
    public Optional<Filiere> findByCode(CodeFiliere code) {
        return Optional.ofNullable(data.get(code));
    }
}
