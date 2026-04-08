package com.ecole221.infrastructure.web.paiement;

import com.ecole221.application.port.in.paiement.ConsulterPaiementsUseCase;
import com.ecole221.application.port.in.paiement.DeclarerPaiementUseCase;
import com.ecole221.application.query.paiement.ConsulterPaiementQuery;
import com.ecole221.application.query.paiement.PaiementView;
import com.ecole221.infrastructure.web.anneeacdemique.AnneeAcademiqueMapper;
import com.ecole221.infrastructure.web.anneeacdemique.request.CreerAnneeAcademiqueRequest;
import com.ecole221.infrastructure.web.paiement.request.DeclarerPaiementRequest;
import com.ecole221.infrastructure.web.validation.CheckPreuvePaiement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/paiements")
public class PaiementController {
    private final DeclarerPaiementUseCase paiementUseCase;
    private final PaiementMapper paiementMapper;
    private final ConsulterPaiementsUseCase consulterPaiementsUseCase;

    public PaiementController(DeclarerPaiementUseCase paiementUseCase, PaiementMapper paiementMapper, ConsulterPaiementsUseCase consulterPaiementsUseCase) {
        this.paiementUseCase = paiementUseCase;
        this.paiementMapper = paiementMapper;
        this.consulterPaiementsUseCase = consulterPaiementsUseCase;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> creer(
            @RequestPart("data") @Valid DeclarerPaiementRequest data,
            @RequestPart("preuvePaiement") @Valid MultipartFile preuvePaiement
    ) {

        CheckPreuvePaiement.validerPreuvePaiement(data.canalPaiement(), preuvePaiement);



        paiementUseCase.execute(paiementMapper.toCommand(data), preuvePaiement);

        URI location = URI.create("/api/paiements/" + data.matricule() + "/"+data.anneeAcademique());

        return ResponseEntity
                .created(location)
                .build();
    }

    @PostMapping("/find")
    public List<PaiementView> consulterPaiement(@RequestBody ConsulterPaiementQuery consulterPaiementQuery){
        return consulterPaiementsUseCase.execute(consulterPaiementQuery);
    }
}
