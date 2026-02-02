package com.ecole221.application.port.out.repository.impl;

import com.ecole221.application.port.out.repository.ClasseRepository;
import com.ecole221.domain.entity.school.Classe;
import com.ecole221.domain.entity.school.CodeClasse;
import com.ecole221.domain.entity.school.NomClasse;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@Profile({"inmemory", "test"})
public class InMemoryClasseRepository implements ClasseRepository {

    private final Map<CodeClasse, Classe> data = new HashMap<>();

    @Override
    public boolean existsByCode(CodeClasse code) {
        return data.containsKey(code);
    }

    @Override
    public boolean existsByNom(NomClasse nom) {
        return data.values().stream()
                .anyMatch(c -> c.getNom().equals(nom)); // on changera si besoin
    }

    @Override
    public void save(Classe classe) {
        data.put(classe.getId(), classe);
    }

    @Override
    public Optional<Classe> findById(CodeClasse code) {
        return Optional.ofNullable(data.get(code));
    }

    @Override
    public Optional<Classe> findByCode(CodeClasse code) {
        return Optional.ofNullable(data.get(code));
    }
}
