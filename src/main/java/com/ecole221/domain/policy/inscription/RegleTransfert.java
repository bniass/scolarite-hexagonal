package com.ecole221.domain.policy.inscription;

import com.ecole221.domain.entity.school.CodeClasse;

import java.time.LocalDate;

public interface RegleTransfert {

    boolean estAutorise(
            LocalDate dateInscription,
            LocalDate dateTransfert,
            CodeClasse codeClasseOrigine,
            CodeClasse codeClasseDestination
    );
}
