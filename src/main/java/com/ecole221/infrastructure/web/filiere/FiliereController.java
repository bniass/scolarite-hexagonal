package com.ecole221.infrastructure.web.filiere;

import com.ecole221.application.port.in.filiere.CreerFiliereUseCase;
import com.ecole221.infrastructure.web.filiere.request.CreerFiliereRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/filiere")
public class FiliereController {
    private final CreerFiliereUseCase creerFiliereUseCase;
    private final FiliereMapper filiereMapper;

    public FiliereController(CreerFiliereUseCase creerFiliereUseCase, FiliereMapper filiereMapper) {
        this.creerFiliereUseCase = creerFiliereUseCase;
        this.filiereMapper = filiereMapper;
    }

    public ResponseEntity<?> creerFiliere(@RequestBody CreerFiliereRequest creerFiliereRequest){
        creerFiliereUseCase.executer(filiereMapper.toCommand(creerFiliereRequest));
        return ResponseEntity.ok("Filiere ajouté!");
    }
}
