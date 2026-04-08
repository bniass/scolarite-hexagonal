package com.ecole221.application.port.in.paiement;

import com.ecole221.application.command.paiement.DeclarerPaiementCommand;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

public interface DeclarerPaiementUseCase {
    void execute(DeclarerPaiementCommand command, @Valid MultipartFile preuvePaiement);
}
