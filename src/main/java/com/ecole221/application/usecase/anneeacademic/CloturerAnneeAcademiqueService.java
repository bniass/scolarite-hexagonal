package com.ecole221.application.usecase.anneeacademic;

import com.ecole221.application.command.anneeacademique.CloturerAnneeAcademiqueCommand;
import com.ecole221.application.port.in.anneeacademique.CloturerAnneeScolaireUseCase;
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
public class CloturerAnneeAcademiqueService
        implements CloturerAnneeScolaireUseCase {

    private final AnneeAcademiqueRepository repository;
    private final DomainEventPublisher eventPublisher;

    public CloturerAnneeAcademiqueService(
            AnneeAcademiqueRepository repository,
            DomainEventPublisher eventPublisher
    ) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void executer(CloturerAnneeAcademiqueCommand cmd) {

        AnneeAcademiqueId id =
                new AnneeAcademiqueId(cmd.codeAnnee()) ;

        AnneeAcademique annee = repository.findByCode(id.value())
                .orElseThrow(() ->
                        new ScolariteNotFoundException(
                                "Année académique introuvable"
                        )
                );

        annee.cloturer();

        repository.save(annee);
        annee.pullDomainEvents()
                .forEach(eventPublisher::publish);
    }
}

