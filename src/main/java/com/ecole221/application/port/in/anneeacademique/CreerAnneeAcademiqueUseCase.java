package com.ecole221.application.port.in.anneeacademique;

import com.ecole221.application.command.anneeacademique.CreerAnneeAcademiqueCommand;

public interface CreerAnneeAcademiqueUseCase {
    void executer(CreerAnneeAcademiqueCommand command);
}
