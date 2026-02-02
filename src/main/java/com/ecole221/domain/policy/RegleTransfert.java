package com.ecole221.domain.policy;

import com.ecole221.domain.entity.school.Classe;

import java.time.LocalDate;

public interface RegleTransfert {

    boolean estAutorise(
            LocalDate dateInscription,
            LocalDate dateTransfert,
            Classe classeSource,
            Classe classeDestination
    );
}
