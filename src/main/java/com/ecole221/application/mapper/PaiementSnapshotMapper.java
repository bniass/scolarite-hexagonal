package com.ecole221.application.mapper;

import com.ecole221.application.command.iscription.CreerInscriptionCommand;
import com.ecole221.application.command.paiement.DeclarerPaiementCommand;
import com.ecole221.application.query.paiement.PaiementView;
import com.ecole221.domain.entity.academic.AnneeAcademiqueId;
import com.ecole221.domain.entity.paiement.Paiement;
import com.ecole221.domain.entity.paiement.PaiementSnapshot;
import com.ecole221.domain.entity.paiement.TypePaiement;
import com.ecole221.domain.entity.school.Montant;
import com.ecole221.domain.entity.student.Matricule;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PaiementSnapshotMapper {

    public PaiementSnapshot toPaiementSnapshot(CreerInscriptionCommand cmd, String urlPreuve){
        return new PaiementSnapshot(
                new Matricule(cmd.matricule()),
                new AnneeAcademiqueId(Integer.parseInt(cmd.anneeAcademique().substring(0, 4))),
                null,
                cmd.canalPaiement(),
                cmd.operateurMobileMoney(),
                cmd.referenceMobileMoney(),
                cmd.nomBanque(),
                cmd.referenceBancaire(),
                new Montant(new BigDecimal(cmd.montantPaye())),
                cmd.datePaiement(),
                cmd.heurePaiement(),
                urlPreuve,
                cmd.nombreDeMois()
        );
    }

    public DeclarerPaiementCommand toDeclarerPaiementCommand(PaiementSnapshot paiementSnapshot){
        return new DeclarerPaiementCommand(
                paiementSnapshot.matricule().value(),
                paiementSnapshot.anneeAcademiqueId().value(),
                null,
                paiementSnapshot.canalPaiement(),
                paiementSnapshot.operateurMobileMoney(),
                paiementSnapshot.referenceMobileMoney(),
                paiementSnapshot.nomBanque(),
                paiementSnapshot.referenceBancaire(),
                paiementSnapshot.montantPaye().valeur().doubleValue(),
                paiementSnapshot.datePaiement(),
                paiementSnapshot.heurePaiement(),
                paiementSnapshot.urlPreuve()
        );
    }

    public PaiementView PaiementToPaiementView(Paiement paiement){
        return new PaiementView(
                paiement.getTypePaiement().name(),
                paiement.getCanalPaiement(),
                paiement.getMontant().valeur().doubleValue(),
                paiement.getDatePaiement(),
                paiement.getMoisAcademique()!= null?paiement.getMoisAcademique().mois()+"":"",
                paiement.getMoisAcademique()!= null?paiement.getMoisAcademique().annee()+"":""
        );
    }
}
