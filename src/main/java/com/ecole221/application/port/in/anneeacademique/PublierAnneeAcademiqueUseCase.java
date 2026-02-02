package com.ecole221.application.port.in.anneeacademique;

import com.ecole221.application.command.anneeacademique.PublierAnneeAcademiqueCommand;

public interface PublierAnneeAcademiqueUseCase {
    void executer(PublierAnneeAcademiqueCommand command);
}
