package com.ecole221.infrastructure.web.inscription;


import com.ecole221.application.command.iscription.CreerInscriptionCommand;
import com.ecole221.infrastructure.web.inscription.request.CreerInscriptionRequest;

public class InscriptionWebMapper {

    public static CreerInscriptionCommand toCommand(
            CreerInscriptionRequest request) {

        return new CreerInscriptionCommand(
                request.codeClasse(),
                request.matricule(),
                request.anneeAcademique(),
                request.nom(),
                request.prenom(),
                request.dateNaissance()
        );
    }
}
