package com.ecole221.application.usecase.classe;

import com.ecole221.application.command.classe.CreerClasseCommand;
import com.ecole221.application.port.in.classe.CreerClasseUseCase;
import com.ecole221.application.port.out.repository.ClasseRepository;
import com.ecole221.application.port.out.repository.FiliereRepository;
import com.ecole221.domain.entity.school.*;
import com.ecole221.domain.exception.ScolariteNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CreerClasseService implements CreerClasseUseCase {

    private final ClasseRepository classeRepository;
    private final FiliereRepository filiereRepository;

    public CreerClasseService(ClasseRepository classeRepository, FiliereRepository filiereRepository) {
        this.classeRepository = classeRepository;
        this.filiereRepository = filiereRepository;
    }

    @Override
    public void executer(CreerClasseCommand cmd) {

        CodeClasse code = new CodeClasse(cmd.codeClasse());
        NomClasse nom = new NomClasse(cmd.nomClasse());

        if (classeRepository.existsByCode(code)) {
            throw new IllegalStateException("Code classe déjà existant");
        }

        if (classeRepository.existsByNom(nom)) {
            throw new IllegalStateException("Nom classe déjà existant");
        }

        Filiere filiere = filiereRepository.findByCode(new CodeFiliere(cmd.codeFiliere()))
                .orElseThrow(() -> new ScolariteNotFoundException("Filière introuvable, créez-la d’abord"));

        Classe classe = new Classe(
                code,
                nom,
                filiere,
                new Montant(cmd.fraisInscription()),
                new Montant(cmd.mensualite()),
                new Montant(cmd.autresFrais())
        );

        classeRepository.save(classe);
    }
}

