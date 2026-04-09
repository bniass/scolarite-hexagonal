package com.ecole221.infrastructure.web.inscription;

import com.ecole221.application.port.in.inscription.CreerInscriptionUseCase;
import com.ecole221.domain.exception.ScolariteException;
import com.ecole221.infrastructure.web.inscription.request.CreerInscriptionRequest;
import com.ecole221.infrastructure.web.validation.CheckPreuvePaiement;
import com.ecole221.infrastructure.web.validation.PaiementSelonCanalValidator;
import com.ecole221.infrastructure.web.validation.ValidPreuvePaiement;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;
import java.util.UUID;

//@Validated
@RestController
@RequestMapping("/api/inscriptions")
public class InscriptionController {

    private final CreerInscriptionUseCase creerInscriptionUseCase;
    private final Validator validator;
    private final ObjectMapper mapper;

    public InscriptionController(
            CreerInscriptionUseCase creerInscriptionUseCase, PaiementSelonCanalValidator validator, Validator validator1, ObjectMapper mapper) {
        this.creerInscriptionUseCase = creerInscriptionUseCase;
        this.validator = validator1;
        this.mapper = mapper;
    }

    //@ValidPreuvePaiement
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<byte[]> creerInscription(
            @RequestPart("data") @Valid  CreerInscriptionRequest data,
            @RequestPart("preuvePaiement") @Valid MultipartFile preuvePaiement
    ) throws Exception {


        CheckPreuvePaiement.validerPreuvePaiement(data.canalPaiement(), preuvePaiement);

        byte[] pdf = creerInscriptionUseCase.executer(
                InscriptionWebMapper.toCommand(data, preuvePaiement.getOriginalFilename()),
                preuvePaiement
        );
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=recu-" + data.matricule()+ UUID.randomUUID() + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
