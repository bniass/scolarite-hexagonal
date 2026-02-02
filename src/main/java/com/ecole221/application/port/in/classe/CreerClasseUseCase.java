package com.ecole221.application.port.in.classe;

import com.ecole221.application.command.classe.CreerClasseCommand;

public interface CreerClasseUseCase {
    void executer(CreerClasseCommand command);
}
