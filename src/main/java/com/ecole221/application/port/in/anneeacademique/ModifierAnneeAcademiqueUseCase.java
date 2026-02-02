package com.ecole221.application.port.in.anneeacademique;

import com.ecole221.application.command.anneeacademique.ModifierAnneeAcademiqueCommand;

public interface ModifierAnneeAcademiqueUseCase {
    void executer(ModifierAnneeAcademiqueCommand command);

}
