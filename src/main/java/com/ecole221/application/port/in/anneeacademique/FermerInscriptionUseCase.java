package com.ecole221.application.port.in.anneeacademique;

import com.ecole221.application.command.anneeacademique.FermerInscriptionCommand;
import com.ecole221.application.command.anneeacademique.OuvrirInscriptionCommand;

public interface FermerInscriptionUseCase {
    void executer(FermerInscriptionCommand command);
}
