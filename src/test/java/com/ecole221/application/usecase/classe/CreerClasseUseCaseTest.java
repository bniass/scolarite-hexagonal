package com.ecole221.application.usecase.classe;

import com.ecole221.application.port.out.repository.ClasseRepository;
import com.ecole221.application.port.out.repository.FiliereRepository;
import com.ecole221.application.port.out.repository.impl.InMemoryClasseRepository;
import com.ecole221.application.port.out.repository.impl.InMemoryFiliereRepository;
import com.ecole221.application.command.classe.CreerClasseCommand;
import com.ecole221.application.port.in.classe.CreerClasseUseCase;
import com.ecole221.domain.entity.school.CodeClasse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class CreerClasseUseCaseTest {

    private ClasseRepository repository = new InMemoryClasseRepository();
    private FiliereRepository filiereRepository = new InMemoryFiliereRepository();

    private CreerClasseUseCase useCase =
            new CreerClasseService(repository, filiereRepository);

    @Test
    public void creation_classe_valide_est_effectuee() {
        CreerClasseCommand cmd = new CreerClasseCommand(
                "L3-INFO-A",
                "Licence 3 Informatique A",
                "INFO",
                new BigDecimal("20000"),
                new BigDecimal("60000"),
                new BigDecimal("10000")
        );

        useCase.executer(cmd);

        assertTrue(repository.existsByCode(new CodeClasse("L3-INFO-A")));
    }
}

