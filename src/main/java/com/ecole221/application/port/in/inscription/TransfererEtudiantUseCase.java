package com.ecole221.application.port.in.inscription;

import com.ecole221.application.command.iscription.TransfererEtudiantCommand;

public interface TransfererEtudiantUseCase {
    void executer(TransfererEtudiantCommand cmd);
}
