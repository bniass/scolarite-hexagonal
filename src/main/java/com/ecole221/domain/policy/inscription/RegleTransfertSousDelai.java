package com.ecole221.domain.policy.inscription;

import com.ecole221.domain.entity.school.CodeClasse;

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
            CodeClasse classeSource,
            CodeClasse classeDestination
    ) {
        return !dateInscription
                .plusMonths(delaiEnMois)
                .isBefore(dateTransfert);
    }
}
