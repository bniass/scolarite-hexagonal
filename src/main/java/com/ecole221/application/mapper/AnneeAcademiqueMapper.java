package com.ecole221.application.mapper;

import com.ecole221.application.query.anneeacademique.AnneeAcademiqueView;
import com.ecole221.application.query.anneeacademique.MoisAcademiqueView;
import com.ecole221.domain.entity.academic.AnneeAcademique;
import org.springframework.stereotype.Component;

@Component
public class AnneeAcademiqueMapper {

    public AnneeAcademiqueView toView(AnneeAcademique annee) {

        return new AnneeAcademiqueView(
                annee.getId().value(),
                annee.getDateDebut(),
                annee.getDateFin(),
                annee.getDateOuvertureInscription(),
                annee.getDateFinInscription(),
                annee.moisAcademiques().stream()
                        .map(m -> new MoisAcademiqueView(
                                m.mois(),
                                m.annee()
                        ))
                        .toList()
        );
    }
}
