package com.ecole221.infrastructure.web.filiere;

import com.ecole221.application.command.filiere.CreerFiliereCommand;
import com.ecole221.infrastructure.web.filiere.request.CreerFiliereRequest;
import org.springframework.stereotype.Component;

@Component
public class FiliereMapper {
    public CreerFiliereCommand toCommand(CreerFiliereRequest creerFiliereRequest){
        return new CreerFiliereCommand(
                creerFiliereRequest.codeClasse(),
                creerFiliereRequest.nomClasse()
        );
    }
}
