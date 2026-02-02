package com.ecole221.application.port.in.anneeacademique;

import com.ecole221.application.command.anneeacademique.OuvrirInscriptionCommand;

public interface OuvrirInscriptionUseCase {
    void executer(OuvrirInscriptionCommand command);
}
