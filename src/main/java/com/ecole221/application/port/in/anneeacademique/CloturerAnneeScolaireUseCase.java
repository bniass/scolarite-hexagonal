package com.ecole221.application.port.in.anneeacademique;

import com.ecole221.application.command.anneeacademique.CloturerAnneeAcademiqueCommand;

public interface CloturerAnneeScolaireUseCase {
    void executer(CloturerAnneeAcademiqueCommand command);
}
