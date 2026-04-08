package com.ecole221.application.usecase.anneeacademic;

import com.ecole221.application.command.anneeacademique.OuvrirInscriptionCommand;
import com.ecole221.application.command.anneeacademique.SuspendreInscriptionCommand;
import com.ecole221.application.port.in.anneeacademique.OuvrirInscriptionUseCase;
import com.ecole221.application.port.in.anneeacademique.SuspendreInscriptionUseCase;
import com.ecole221.application.port.out.event.DomainEventPublisher;
import com.ecole221.application.port.out.repository.AnneeAcademiqueRepository;
import com.ecole221.domain.entity.academic.AnneeAcademique;
import com.ecole221.domain.entity.academic.AnneeAcademiqueId;
import com.ecole221.domain.exception.ScolariteNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SuspendreInscriptionService
        implements SuspendreInscriptionUseCase {

    private final AnneeAcademiqueRepository repository;
    private final DomainEventPublisher eventPublisher;


    public SuspendreInscriptionService(
            AnneeAcademiqueRepository repository, DomainEventPublisher eventPublisher
    ) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void executer(SuspendreInscriptionCommand cmd) {

        AnneeAcademiqueId id =
                new AnneeAcademiqueId(cmd.codeAnnee()) ;

        AnneeAcademique annee = repository.findByCode(id.value())
                .orElseThrow(() ->
                        new ScolariteNotFoundException(
                                "Année académique introuvable"
                        )
                );

        annee.suspendreInscriptions();
        repository.save(annee);
        annee.pullDomainEvents()
                .forEach(eventPublisher::publish);
    }
}

