package com.ecole221.application.usecase.inscription;

import com.ecole221.application.command.iscription.TransfererEtudiantCommand;
import com.ecole221.application.port.in.inscription.TransfererEtudiantUseCase;
import com.ecole221.application.port.out.repository.ClasseRepository;
import com.ecole221.application.port.out.repository.InscriptionRepository;
import com.ecole221.domain.policy.inscription.RegleTransfert;
import com.ecole221.domain.entity.school.CodeClasse;
import com.ecole221.domain.exception.ScolariteNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Clock;

@Service
public class TransfererEtudiantService implements TransfererEtudiantUseCase {

    private final InscriptionRepository inscriptionRepository;
    private final ClasseRepository classeRepository;
    private final Clock clock; // pour gérer le temps proprement (injection)
    private final RegleTransfert regleTransfert;

    public TransfererEtudiantService(
            InscriptionRepository inscriptionRepository,
            ClasseRepository classeRepository,
            Clock clock, RegleTransfert regleTransfert // injection recommended
    ) {
        this.inscriptionRepository = inscriptionRepository;
        this.classeRepository = classeRepository;
        this.clock = clock;
        this.regleTransfert = regleTransfert;
    }

    @Override
    public void executer(TransfererEtudiantCommand cmd) {

        var inscription = inscriptionRepository
                .findByMatriculeAndAnnee(cmd.matricule(), cmd.anneeAcademique())
                .orElseThrow(() -> new ScolariteNotFoundException("Inscription introuvable"));

        if (!inscription.getCodeClasse().value().equals(cmd.codeClasseOrigine())) {
            throw new IllegalStateException("L'étudiant n'est pas inscrit dans la classe d'origine");
        }

        var nouvelleClasse = classeRepository
                .findByCode(new CodeClasse(cmd.codeNouvelleClasse()))
                .orElseThrow(() -> new IllegalStateException("Nouvelle classe introuvable"));


        inscription.transfererVers(
                nouvelleClasse.getCode(),
                cmd.dateTransfert(),
                regleTransfert
        );

        inscriptionRepository.save(inscription); // commit du changement
    }
}

