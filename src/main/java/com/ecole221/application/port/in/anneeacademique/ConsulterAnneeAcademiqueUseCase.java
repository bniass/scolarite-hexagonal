package com.ecole221.application.port.in.anneeacademique;

import com.ecole221.application.query.anneeacademique.AnneeAcademiqueView;

public interface ConsulterAnneeAcademiqueUseCase {
    AnneeAcademiqueView executer(String code);
}
