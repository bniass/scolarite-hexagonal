package com.ecole221.application.port.in.inscription;

import com.ecole221.application.command.iscription.CreerInscriptionCommand;

public interface CreerInscriptionUseCase {
    void executer(CreerInscriptionCommand command);
}
