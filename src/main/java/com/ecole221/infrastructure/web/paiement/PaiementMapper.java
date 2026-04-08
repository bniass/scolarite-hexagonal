package com.ecole221.infrastructure.web.paiement;

import com.ecole221.application.command.paiement.DeclarerPaiementCommand;
import com.ecole221.application.query.paiement.PaiementView;
import com.ecole221.domain.entity.paiement.TypePaiement;
import com.ecole221.infrastructure.web.paiement.request.DeclarerPaiementRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaiementMapper {
    public DeclarerPaiementCommand toCommand(DeclarerPaiementRequest request) {
        return new DeclarerPaiementCommand(
                request.matricule(),
                request.anneeAcademique(),
                TypePaiement.MENSUALITE.name(),
                request.canalPaiement(),
                request.operateurMobileMoney(),
                request.referenceMobileMoney(),
                request.nomBanque(),
                request.referenceBancaire(),
                request.montantPaye(),
                request.datePaiement(),
                request.heurePaiement(),
                request.preuvePaiement()
        );
    }
}
