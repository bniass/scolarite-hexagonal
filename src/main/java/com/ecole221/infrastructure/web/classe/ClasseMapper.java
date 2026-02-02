package com.ecole221.infrastructure.web.classe;

import com.ecole221.application.command.classe.CreerClasseCommand;
import com.ecole221.application.command.classe.ModifierAutresFraisClasseCommand;
import com.ecole221.application.command.classe.ModifierMensualiteClasseCommand;
import com.ecole221.application.command.classe.ModifierFraisInscriptionClasseCommand;
import com.ecole221.infrastructure.web.classe.request.CreerClasseRequest;
import com.ecole221.infrastructure.web.classe.request.ModifierAutresFraisRequest;
import com.ecole221.infrastructure.web.classe.request.ModifierFraisInscriptionRequest;
import com.ecole221.infrastructure.web.classe.request.ModifierMensualiteRequest;
import org.springframework.stereotype.Component;

@Component
public class ClasseMapper {

    public CreerClasseCommand toCommand(CreerClasseRequest request) {
        return new CreerClasseCommand(
                request.codeClasse(),
                request.nomClasse(),
                request.codeFiliere(),
                request.fraisInscription(),
                request.mensualite(),
                request.autresFrais()
        );
    }

    public ModifierFraisInscriptionClasseCommand toCommand(ModifierFraisInscriptionRequest request, String codeClasse) {
        return new ModifierFraisInscriptionClasseCommand(codeClasse, request.nouveauMontant());
    }

    public ModifierMensualiteClasseCommand toCommand(ModifierMensualiteRequest request, String codeClasse) {
        return new ModifierMensualiteClasseCommand(codeClasse, request.nouvelleMensualite());
    }

    public ModifierAutresFraisClasseCommand toCommand(ModifierAutresFraisRequest request, String codeClasse) {
        return new ModifierAutresFraisClasseCommand(codeClasse, request.nouveauxAutresFrais());
    }
}
