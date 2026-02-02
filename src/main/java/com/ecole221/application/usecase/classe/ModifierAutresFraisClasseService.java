package com.ecole221.application.usecase.classe;

import com.ecole221.application.command.classe.ModifierAutresFraisClasseCommand;
import com.ecole221.application.port.in.classe.ModifierAutresFraisClasseUseCase;
import com.ecole221.application.port.out.repository.ClasseRepository;
import com.ecole221.domain.entity.school.Classe;
import com.ecole221.domain.entity.school.CodeClasse;
import com.ecole221.domain.entity.school.Montant;
import com.ecole221.domain.exception.ScolariteNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ModifierAutresFraisClasseService implements ModifierAutresFraisClasseUseCase {

    private final ClasseRepository repository;

    public ModifierAutresFraisClasseService(ClasseRepository repository) {
        this.repository = repository;
    }

    @Override
    public void executer(ModifierAutresFraisClasseCommand cmd) {

        CodeClasse code = new CodeClasse(cmd.codeClasse());

        Classe classe = repository.findById(code)
                .orElseThrow(() -> new ScolariteNotFoundException(
                        "Classe introuvable : " + cmd.codeClasse()
                ));

        // Création des nouveaux Value Objects
        Montant nouveauxAutresFrais = new Montant(cmd.nouveauxAutresFrais());

        // Règles métier déléguées à l’agrégat
        classe.modifierAutresFrais(nouveauxAutresFrais);

        // Sauvegarde de l’état cohérent
        repository.save(classe);
    }
}
