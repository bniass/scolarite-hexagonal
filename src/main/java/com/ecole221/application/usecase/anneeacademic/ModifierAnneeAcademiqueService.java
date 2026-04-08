package com.ecole221.application.usecase.anneeacademic;

import com.ecole221.application.command.anneeacademique.CreerAnneeAcademiqueCommand;
import com.ecole221.application.command.anneeacademique.ModifierAnneeAcademiqueCommand;
import com.ecole221.application.port.in.anneeacademique.CreerAnneeAcademiqueUseCase;
import com.ecole221.application.port.in.anneeacademique.ModifierAnneeAcademiqueUseCase;
import com.ecole221.application.port.out.event.DomainEventPublisher;
import com.ecole221.application.port.out.repository.AnneeAcademiqueRepository;
import com.ecole221.domain.entity.academic.AnneeAcademique;
import com.ecole221.domain.entity.academic.AnneeAcademiqueId;
import com.ecole221.domain.entity.academic.DatesAnnee;
import com.ecole221.domain.event.shared.DomainEvent;
import com.ecole221.domain.exception.AnneeAcadiqueException;
import com.ecole221.domain.exception.ScolariteNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ModifierAnneeAcademiqueService
        implements ModifierAnneeAcademiqueUseCase {

    private final AnneeAcademiqueRepository repository;
    private final DomainEventPublisher eventPublisher;


    public ModifierAnneeAcademiqueService(
            AnneeAcademiqueRepository repository, DomainEventPublisher eventPublisher
    ) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    @Override
    public void executer(ModifierAnneeAcademiqueCommand cmd) {

        AnneeAcademiqueId id =
                new AnneeAcademiqueId(cmd.code());

        Optional<AnneeAcademique> annee = repository.findByCode(id.value());
        if (!annee.isPresent()) {
            throw new ScolariteNotFoundException(
                    "Cette année académique n'existe pas!"
            );
        }

        AnneeAcademique toUpdated = annee.get();
        DatesAnnee datesAnnee = new DatesAnnee(cmd.dateDebut(),
                cmd.dateFin(),
                cmd.dateDebutInscriptions(),
                cmd.dateFinInscriptions());
        toUpdated.modifier(datesAnnee);

        repository.save(toUpdated);

        toUpdated.pullDomainEvents()
                .forEach(eventPublisher::publish);
    }
}

