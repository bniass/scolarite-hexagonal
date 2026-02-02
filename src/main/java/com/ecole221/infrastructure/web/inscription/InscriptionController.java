package com.ecole221.infrastructure.web.inscription;

import com.ecole221.application.port.in.inscription.CreerInscriptionUseCase;
import com.ecole221.infrastructure.web.inscription.request.CreerInscriptionRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inscriptions")
public class InscriptionController {

    private final CreerInscriptionUseCase creerInscriptionUseCase;

    public InscriptionController(
            CreerInscriptionUseCase creerInscriptionUseCase) {
        this.creerInscriptionUseCase = creerInscriptionUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void creerInscription(
            @RequestBody @Valid CreerInscriptionRequest request) {

        creerInscriptionUseCase.executer(
                InscriptionWebMapper.toCommand(request)
        );
    }
}
