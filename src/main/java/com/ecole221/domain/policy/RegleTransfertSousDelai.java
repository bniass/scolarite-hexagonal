package com.ecole221.domain.policy;

import com.ecole221.domain.entity.school.Classe;

import java.time.LocalDate;

public class RegleTransfertSousDelai implements RegleTransfert {

    private final int delaiEnMois;

    public RegleTransfertSousDelai(int delaiEnMois) {
        this.delaiEnMois = delaiEnMois;
    }

    @Override
    public boolean estAutorise(
            LocalDate dateInscription,
            LocalDate dateTransfert,
            Classe classeSource,
            Classe classeDestination
    ) {
        return !dateInscription
                .plusMonths(delaiEnMois)
                .isBefore(dateTransfert);
    }
}
