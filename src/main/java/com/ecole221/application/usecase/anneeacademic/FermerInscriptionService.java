package com.ecole221.application.usecase.anneeacademic;

import com.ecole221.application.command.anneeacademique.FermerInscriptionCommand;
import com.ecole221.application.command.anneeacademique.OuvrirInscriptionCommand;
import com.ecole221.application.port.in.anneeacademique.FermerInscriptionUseCase;
import com.ecole221.application.port.in.anneeacademique.OuvrirInscriptionUseCase;
import com.ecole221.application.port.out.event.DomainEventPublisher;
import com.ecole221.application.port.out.repository.AnneeAcademiqueRepository;
import com.ecole221.domain.entity.academic.AnneeAcademique;
import com.ecole221.domain.entity.academic.AnneeAcademiqueId;
import com.ecole221.domain.exception.ScolariteNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FermerInscriptionService
        implements FermerInscriptionUseCase {

    private final AnneeAcademiqueRepository repository;
    private final DomainEventPublisher eventPublisher;


    public FermerInscriptionService(
            AnneeAcademiqueRepository repository, DomainEventPublisher eventPublisher
    ) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    @Override
    public void executer(FermerInscriptionCommand cmd) {

        AnneeAcademiqueId id =
                new AnneeAcademiqueId(cmd.codeAnnee()) ;

        AnneeAcademique annee = repository.findByCode(id.value())
                .orElseThrow(() ->
                        new ScolariteNotFoundException(
                                "Année académique introuvable"
                        )
                );

        annee.fermerInscriptions();
        repository.save(annee);
        annee.pullDomainEvents()
                .forEach(eventPublisher::publish);
    }
}

