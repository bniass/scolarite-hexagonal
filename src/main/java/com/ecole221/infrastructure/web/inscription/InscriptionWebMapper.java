package com.ecole221.infrastructure.web.inscription;


import com.ecole221.application.command.iscription.CreerInscriptionCommand;
import com.ecole221.application.command.paiement.DeclarerPaiementCommand;
import com.ecole221.domain.entity.paiement.NombreDeMois;
import com.ecole221.infrastructure.web.inscription.request.CreerInscriptionRequest;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;

public class InscriptionWebMapper {

    public static CreerInscriptionCommand toCommand(
            CreerInscriptionRequest request, String preuvePaiement) {

        return new CreerInscriptionCommand(
                request.codeClasse(),
                request.matricule(),
                request.anneeAcademique(),
                request.nom(),
                request.prenom(),
                request.dateNaissance(),
                request.typePaiement(),
                request.canalPaiement(),
                request.operateurMobileMoney(),
                request.referenceMobileMoney(),
                request.nomBanque(),
                request.referenceBancaire(),
                request.montantPaye(),
                request.datePaiement(),
                request.heurePaiement(),
                preuvePaiement,
                request.nombreDeMois()
        );
    }
}
