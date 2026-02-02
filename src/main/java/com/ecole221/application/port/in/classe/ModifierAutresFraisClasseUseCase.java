package com.ecole221.application.port.in.classe;

import com.ecole221.application.command.classe.ModifierAutresFraisClasseCommand;

public interface ModifierAutresFraisClasseUseCase {
    void executer(ModifierAutresFraisClasseCommand command);
}
