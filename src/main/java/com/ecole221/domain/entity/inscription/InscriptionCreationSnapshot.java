package com.ecole221.domain.entity.inscription;

import com.ecole221.domain.entity.academic.MoisAcademique;
import com.ecole221.domain.entity.paiement.PaiementSnapshot;
import com.ecole221.domain.entity.school.CodeClasse;

import java.util.List;

public record InscriptionCreationSnapshot(
        InscriptionId inscriptionId,
        CodeClasse codeClasse,
        PaiementSnapshot paiementSnapshot,
        List<MoisAcademique> moisAcademiques,
        String sagaId
) {
}
