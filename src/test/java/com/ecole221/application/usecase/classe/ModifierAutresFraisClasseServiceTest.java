package com.ecole221.application.usecase.classe;

import com.ecole221.application.port.out.repository.ClasseRepository;
import com.ecole221.application.port.out.repository.FiliereRepository;
import com.ecole221.application.port.out.repository.impl.InMemoryClasseRepository;
import com.ecole221.application.port.out.repository.impl.InMemoryFiliereRepository;
import com.ecole221.application.command.classe.CreerClasseCommand;
import com.ecole221.application.command.classe.ModifierAutresFraisClasseCommand;
import com.ecole221.application.command.filiere.CreerFiliereCommand;
import com.ecole221.application.usecase.filiere.CreerFiliereService;
import com.ecole221.domain.entity.school.Classe;
import com.ecole221.domain.entity.school.CodeClasse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ModifierAutresFraisClasseServiceTest {
    @Test
    public void modifier_autres_frais_met_a_jour_total() {
        ClasseRepository repo = new InMemoryClasseRepository();
        FiliereRepository filiereRepository = new InMemoryFiliereRepository();

        new CreerFiliereService(filiereRepository).executer(
               new CreerFiliereCommand(
                       "INFO",
                       "Informatique"
               )
        );

        new CreerClasseService(repo, filiereRepository).executer(
                new CreerClasseCommand(
                        "L3-INFO-C", "Licence 3 Informatique C",
                        "INFO",
                        BigDecimal.valueOf(20000),
                        BigDecimal.valueOf(60000),
                        BigDecimal.valueOf(10000)
                )
        );

        new ModifierAutresFraisClasseService(repo).executer(
                new ModifierAutresFraisClasseCommand(
                        "L3-INFO-C", BigDecimal.valueOf(5000)
                )
        );

        Classe classe = repo.findById(new CodeClasse("L3-INFO-C")).get();

        assertEquals(
                BigDecimal.valueOf(85000), // 20000 + 60000 + 5000
                classe.montantTotalInscription().valeur()
        );
    }
}
