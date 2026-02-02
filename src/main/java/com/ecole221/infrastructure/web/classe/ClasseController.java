package com.ecole221.infrastructure.web.classe;

import com.ecole221.application.port.in.classe.CreerClasseUseCase;
import com.ecole221.application.port.in.classe.ModifierAutresFraisClasseUseCase;
import com.ecole221.application.port.in.classe.ModifierMensualiteClasseUseCase;
import com.ecole221.application.port.in.classe.ModifierFraisInscriptionClasseUseCase;
import com.ecole221.infrastructure.web.classe.request.CreerClasseRequest;
import com.ecole221.infrastructure.web.classe.request.ModifierAutresFraisRequest;
import com.ecole221.infrastructure.web.classe.request.ModifierFraisInscriptionRequest;
import com.ecole221.infrastructure.web.classe.request.ModifierMensualiteRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/classes")
public class ClasseController {

    private final CreerClasseUseCase creerClasseUseCase;
    private final ModifierFraisInscriptionClasseUseCase modifierFraisUseCase;
    private final ModifierMensualiteClasseUseCase modifierMensualiteUseCase;
    private final ModifierAutresFraisClasseUseCase modifierAutresFraisUseCase;
    private final ClasseMapper mapper;

    public ClasseController(
            CreerClasseUseCase creerClasseUseCase,
            ModifierFraisInscriptionClasseUseCase modifierFraisUseCase,
            ModifierMensualiteClasseUseCase modifierMensualiteUseCase,
            ModifierAutresFraisClasseUseCase modifierAutresFraisUseCase,
            ClasseMapper mapper
    ) {
        this.creerClasseUseCase = creerClasseUseCase;
        this.modifierFraisUseCase = modifierFraisUseCase;
        this.modifierMensualiteUseCase = modifierMensualiteUseCase;
        this.modifierAutresFraisUseCase = modifierAutresFraisUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<?> creer(@RequestBody CreerClasseRequest req) {
        creerClasseUseCase.executer(mapper.toCommand(req));
        return ResponseEntity.ok("Classe créée avec succès");
    }

    @PatchMapping("/{codeClasse}/frais-inscription")
    public ResponseEntity<?> modifierFrais(
            @PathVariable String codeClasse,
            @RequestBody ModifierFraisInscriptionRequest req
    ) {
        modifierFraisUseCase.executer(mapper.toCommand(req, codeClasse));
        return ResponseEntity.ok("Frais d'inscription modifiés");
    }

    @PatchMapping("/{codeClasse}/mensualite")
    public ResponseEntity<?> modifierMensualite(
            @PathVariable String codeClasse,
            @RequestBody ModifierMensualiteRequest req
    ) {
        modifierMensualiteUseCase.executer(mapper.toCommand(req, codeClasse));
        return ResponseEntity.ok("Mensualité modifiée");
    }

    @PatchMapping("/{codeClasse}/autres-frais")
    public ResponseEntity<?> modifierAutresFrais(@PathVariable String codeClasse, @RequestBody ModifierAutresFraisRequest req) {
        modifierAutresFraisUseCase.executer(mapper.toCommand(req, codeClasse));
        return ResponseEntity.ok("Autres frais modifiés");
    }
}

