package com.ecole221.infrastructure.web.anneeacdemique;

import com.ecole221.application.command.anneeacademique.CreerAnneeAcademiqueCommand;
import com.ecole221.infrastructure.web.anneeacdemique.request.CreerAnneeAcademiqueRequest;

public class AnneeAcademiqueMapper {
    public static CreerAnneeAcademiqueCommand toCommand(
            CreerAnneeAcademiqueRequest req
    ) {
        return new CreerAnneeAcademiqueCommand(
                Integer.parseInt(req.code()),
                req.dateDebut(),
                req.dateFin(),
                req.dateDebutInscriptions(),
                req.dateFinInscriptions()
        );
    }


}
