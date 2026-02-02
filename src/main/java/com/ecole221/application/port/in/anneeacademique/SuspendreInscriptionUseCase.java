package com.ecole221.application.port.in.anneeacademique;

import com.ecole221.application.command.anneeacademique.SuspendreInscriptionCommand;

public interface SuspendreInscriptionUseCase {
    void executer(SuspendreInscriptionCommand command);
}
