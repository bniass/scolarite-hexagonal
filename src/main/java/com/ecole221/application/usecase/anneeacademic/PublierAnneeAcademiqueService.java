package com.ecole221.application.usecase.anneeacademic;

import com.ecole221.application.command.anneeacademique.PublierAnneeAcademiqueCommand;
import com.ecole221.application.port.in.anneeacademique.PublierAnneeAcademiqueUseCase;
import com.ecole221.application.port.out.event.DomainEventPublisher;
import com.ecole221.application.port.out.repository.AnneeAcademiqueRepository;
import com.ecole221.domain.entity.academic.AnneeAcademique;
import com.ecole221.domain.entity.academic.AnneeAcademiqueId;
import com.ecole221.domain.event.shared.DomainEvent;
import com.ecole221.domain.exception.AnneeAcadiqueException;
import com.ecole221.domain.exception.ScolariteNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublierAnneeAcademiqueService
        implements PublierAnneeAcademiqueUseCase {

    private final AnneeAcademiqueRepository repository;
    private final DomainEventPublisher eventPublisher;


    public PublierAnneeAcademiqueService(AnneeAcademiqueRepository repository, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void executer(PublierAnneeAcademiqueCommand cmd) {

        AnneeAcademiqueId id =
                new AnneeAcademiqueId(cmd.codeAnnee()) ;

        AnneeAcademique annee = repository.findByCode(id.value())
                .orElseThrow(() ->
                        new ScolariteNotFoundException("Année académique introuvable"));

        annee.publier();
        repository.save(annee);

        annee.pullDomainEvents()
                .forEach(eventPublisher::publish);
    }
}

