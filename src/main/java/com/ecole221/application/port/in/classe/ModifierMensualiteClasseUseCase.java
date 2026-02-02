package com.ecole221.application.port.in.classe;

import com.ecole221.application.command.classe.ModifierMensualiteClasseCommand;

public interface ModifierMensualiteClasseUseCase {
    void executer(ModifierMensualiteClasseCommand command);
}
