package com.ecole221.application.port.in.classe;

import com.ecole221.application.command.classe.ModifierFraisInscriptionClasseCommand;

public interface ModifierFraisInscriptionClasseUseCase {
    void executer(ModifierFraisInscriptionClasseCommand command);
}
