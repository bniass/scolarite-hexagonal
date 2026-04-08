package com.ecole221.application.port.in.inscription;

import com.ecole221.application.command.iscription.CreerInscriptionCommand;
import org.springframework.web.multipart.MultipartFile;

public interface CreerInscriptionUseCase {
    void executer(CreerInscriptionCommand command, MultipartFile preuvePaiement);
}
