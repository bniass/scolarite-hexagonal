package com.ecole221.application.usecase.filiere;

import com.ecole221.application.command.filiere.CreerFiliereCommand;
import com.ecole221.application.port.in.filiere.CreerFiliereUseCase;
import com.ecole221.application.port.out.repository.FiliereRepository;
import com.ecole221.domain.entity.school.CodeFiliere;
import com.ecole221.domain.entity.school.Filiere;
import com.ecole221.domain.entity.school.NomFiliere;
import org.springframework.stereotype.Service;

@Service
public class CreerFiliereService implements CreerFiliereUseCase {

    private final FiliereRepository repository;

    public CreerFiliereService(FiliereRepository repository) {
        this.repository = repository;
    }

    @Override
    public void executer(CreerFiliereCommand cmd) {

        CodeFiliere code = new CodeFiliere(cmd.codeFiliere());
        NomFiliere nom = new NomFiliere(cmd.nomFiliere());

        if (repository.existsByCode(code)) {
            throw new IllegalStateException("La filière existe déjà");
        }

        Filiere filiere = new Filiere(code, nom);

        repository.save(filiere);
    }
}
