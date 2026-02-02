package com.ecole221.infrastructure.persistence.classe.adapter;

import com.ecole221.application.port.out.repository.ClasseRepository;
import com.ecole221.domain.entity.school.Classe;
import com.ecole221.domain.entity.school.CodeClasse;
import com.ecole221.domain.entity.school.NomClasse;
import com.ecole221.infrastructure.persistence.classe.mapper.ClassePersistenceMapper;
import com.ecole221.infrastructure.persistence.classe.repository.ClasseJpaRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Profile("mysql")
public class ClasseMysqlRepositoryAdapter implements ClasseRepository {

    private final ClasseJpaRepository classeJpaRepository;
    private final ClassePersistenceMapper mapper;

    public ClasseMysqlRepositoryAdapter(
            ClasseJpaRepository classeJpaRepository,
            ClassePersistenceMapper mapper) {
        this.classeJpaRepository = classeJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public boolean existsByCode(CodeClasse code) {
        return classeJpaRepository.existsByCode(code.value());
    }

    @Override
    public boolean existsByNom(NomClasse nom) {
        return classeJpaRepository.existsByNom(nom.value());
    }

    @Override
    public void save(Classe classe) {
        classeJpaRepository.save(
                mapper.toJpa(classe)
        );
    }

    @Override
    public Optional<Classe> findById(CodeClasse code) {
        // ici, l’identifiant fonctionnel est le code
        return findByCode(code);
    }

    @Override
    public Optional<Classe> findByCode(CodeClasse code) {
        return classeJpaRepository
                .findByCode(code.value())
                .map(mapper::toDomain);
    }
}
