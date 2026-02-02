package com.ecole221.application.usecase.classe;

import com.ecole221.application.command.classe.ModifierFraisInscriptionClasseCommand;
import com.ecole221.application.port.in.classe.ModifierFraisInscriptionClasseUseCase;
import com.ecole221.application.port.out.repository.ClasseRepository;
import com.ecole221.domain.entity.school.Classe;
import com.ecole221.domain.entity.school.CodeClasse;
import com.ecole221.domain.entity.school.Montant;
import com.ecole221.domain.exception.ScolariteNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ModifierFraisInscriptionClasseService implements ModifierFraisInscriptionClasseUseCase {

    private final ClasseRepository repository;

    public ModifierFraisInscriptionClasseService(ClasseRepository repository) {
        this.repository = repository;
    }

    @Override
    public void executer(ModifierFraisInscriptionClasseCommand cmd) {

        CodeClasse code = new CodeClasse(cmd.codeClasse());

        Classe classe = repository.findById(code)
                .orElseThrow(() -> new ScolariteNotFoundException(
                        "Classe introuvable : " + cmd.codeClasse()
                ));

        // Création des nouveaux Value Objects
        Montant nouveauxAutresFrais = new Montant(cmd.nouveauxFraisInscription());
        classe.modifierFraisInscrition(nouveauxAutresFrais);

        // Sauvegarde de l’état cohérent
        repository.save(classe);
    }
}
