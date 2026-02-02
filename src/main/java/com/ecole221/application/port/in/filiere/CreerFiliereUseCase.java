package com.ecole221.application.port.in.filiere;

import com.ecole221.application.command.filiere.CreerFiliereCommand;

public interface CreerFiliereUseCase {
    public void executer(CreerFiliereCommand cmd);
}
