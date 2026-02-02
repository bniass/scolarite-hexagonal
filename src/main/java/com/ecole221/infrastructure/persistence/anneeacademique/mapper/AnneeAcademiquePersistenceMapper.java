package com.ecole221.infrastructure.persistence.anneeacademique.mapper;

import com.ecole221.domain.entity.academic.AnneeAcademique;
import com.ecole221.domain.entity.academic.DatesAnnee;
import com.ecole221.domain.entity.academic.MoisAcademique;
import com.ecole221.domain.entity.academic.Statut;
import com.ecole221.infrastructure.persistence.anneeacademique.entity.AnneeAcademiqueJpaEntity;
import com.ecole221.infrastructure.persistence.anneeacademique.entity.MoisAcademiqueEmbeddable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AnneeAcademiquePersistenceMapper {

    public AnneeAcademiqueJpaEntity toJpa(AnneeAcademique domain) {

        AnneeAcademiqueJpaEntity entity = new AnneeAcademiqueJpaEntity();
        entity.setCode(domain.getId().value());
        entity.setDateDebut(domain.getDateDebut());
        entity.setDateFin(domain.getDateFin());
        entity.setDateDebutInscriptions(domain.getDateOuvertureInscription());
        entity.setDateFinInscriptions(domain.getDateFinInscription());
        entity.setDatePublication(domain.getDatePublication());
        entity.setStatut(EtatAnneeFactory.toStatut(domain.getEtat()));

        // Mois (ElementCollection) : on modifie la collection, on ne la remplace pas
        entity.getMoisAcademiques().clear();
        domain.getMoisAcademiques().forEach(m ->
                entity.getMoisAcademiques().add(new MoisAcademiqueEmbeddable(m.mois(), m.annee()))
        );

        return entity;
    }

    public AnneeAcademique toDomain(AnneeAcademiqueJpaEntity entity) {

        List<MoisAcademique> mois = entity.getMoisAcademiques()
                .stream()
                .map(m -> new MoisAcademique(m.getMois(), m.getAnnee()))
                .toList();

        return AnneeAcademique.reconstituer(
                entity.getCode(),
                new DatesAnnee(
                        entity.getDateDebut(),
                        entity.getDateFin(),
                        entity.getDateDebutInscriptions(),
                        entity.getDateFinInscriptions()
                ),
                entity.getDatePublication(),
                EtatAnneeFactory.fromStatut(Statut.valueOf(entity.getStatut().name())),
                mois);
    }
}
