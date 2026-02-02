package com.ecole221.infrastructure.persistence.classe.mapper;

import com.ecole221.domain.entity.school.Classe;
import com.ecole221.domain.entity.school.CodeClasse;
import com.ecole221.domain.entity.school.Montant;
import com.ecole221.domain.entity.school.NomClasse;
import com.ecole221.infrastructure.persistence.classe.entity.ClasseJpaEntity;
import com.ecole221.infrastructure.persistence.filiere.mapper.FilierePersistenceMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ClassePersistenceMapper {
    private final FilierePersistenceMapper filierePersistenceMapper;

    public ClassePersistenceMapper(FilierePersistenceMapper filierePersistenceMapper) {
        this.filierePersistenceMapper = filierePersistenceMapper;
    }

    public Classe toDomain(ClasseJpaEntity entity){
        return new Classe(
                new CodeClasse(entity.getCode()),
                new NomClasse(entity.getNom()),
                filierePersistenceMapper.toDomain(entity.getFiliere()),
                new Montant(new BigDecimal(entity.getFraisInscription())),
                new Montant(new BigDecimal(entity.getMensualite())),
                new Montant(new BigDecimal(entity.getAutresFrais()))
        );
    }

    public ClasseJpaEntity toJpa(Classe domain){
        ClasseJpaEntity entity = new ClasseJpaEntity();
        entity.setCode(domain.getCode().value());
        entity.setNom(domain.getNom().value());
        entity.setFiliere(filierePersistenceMapper.toJpa(domain.getFiliere()));
        entity.setFraisInscription(domain.getFraisInscription().valeur().intValue());
        entity.setMensualite(domain.getMensualite().valeur().intValue());
        entity.setAutresFrais(domain.getAutresFrais().valeur().intValue());
        return entity;
    }

}
