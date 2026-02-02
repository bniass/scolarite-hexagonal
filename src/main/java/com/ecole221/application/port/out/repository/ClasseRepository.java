package com.ecole221.application.port.out.repository;


import com.ecole221.domain.entity.school.Classe;
import com.ecole221.domain.entity.school.CodeClasse;
import com.ecole221.domain.entity.school.NomClasse;

import java.util.Optional;

public interface ClasseRepository {

    boolean existsByCode(CodeClasse code);

    boolean existsByNom(NomClasse nom);

    void save(Classe classe);

    Optional<Classe> findById(CodeClasse code);

    Optional<Classe> findByCode(CodeClasse code);
}
