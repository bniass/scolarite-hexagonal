package com.ecole221.infrastructure.persistence.etudiant.mapper;

import com.ecole221.domain.entity.student.Etudiant;
import com.ecole221.domain.entity.student.Matricule;
import com.ecole221.infrastructure.persistence.etudiant.entity.EtudiantJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class EtudiantPersistenceMapper {


    public Etudiant toDomain(EtudiantJpaEntity entity){
        return new Etudiant(
               new Matricule(entity.getMatricule()),
                entity.getNom(),
                entity.getPrenom(),
                entity.getDateNaissance()
        );
    }


    public EtudiantJpaEntity toJpa(Etudiant domain){
        EtudiantJpaEntity entity = new EtudiantJpaEntity();
        entity.setMatricule(domain.getMatricule().value());
        entity.setNom(domain.getNom());
        entity.setPrenom(domain.getPrenom());
        entity.setDateNaissance(domain.getDateNaissance());
        return entity;
    }
}
