package com.ecole221.infrastructure.persistence.anneeacademique.mapper;

import com.ecole221.domain.entity.academic.AnneeAcademique;
import com.ecole221.domain.entity.academic.MoisAcademique;
import com.ecole221.infrastructure.persistence.anneeacademique.entity.AnneeAcademiqueJpaEntity;
import com.ecole221.infrastructure.persistence.anneeacademique.entity.MoisAcademiqueEmbeddable;
import org.springframework.stereotype.Component;

@Component
public class MoisAcademiquePersistenceMapper {

    public void mapMois(
            AnneeAcademique domain,
            AnneeAcademiqueJpaEntity jpa
    ) {
        jpa.getMoisAcademiques().clear();
        domain.getMoisAcademiques().forEach(m ->
                jpa.getMoisAcademiques().add(MoisAcademiqueEmbeddable.fromDomain(m))
        );
    }
}

