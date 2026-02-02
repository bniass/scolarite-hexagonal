package com.ecole221.infrastructure.persistence.filiere.mapper;

import com.ecole221.domain.entity.school.CodeFiliere;
import com.ecole221.domain.entity.school.Filiere;
import com.ecole221.domain.entity.school.NomFiliere;
import com.ecole221.infrastructure.persistence.filiere.entity.FiliereJpaEntity;
import org.springframework.stereotype.Component;
@Component
public class FilierePersistenceMapper {

    public Filiere toDomain(FiliereJpaEntity entity){
        return new Filiere(
                new CodeFiliere(entity.getCode()),
                new NomFiliere(entity.getNom())
        );
    }

    public FiliereJpaEntity toJpa(Filiere domain){
        FiliereJpaEntity entity = new FiliereJpaEntity();
        entity.setCode(domain.getCodeFiliere().value());
        entity.setNom(domain.getNomFiliere().value());
        return entity;
    }

}
