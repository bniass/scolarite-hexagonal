package com.ecole221.application.usecase.anneeacademic;

import com.ecole221.application.command.anneeacademique.CreerAnneeAcademiqueCommand;
import com.ecole221.application.port.in.anneeacademique.CreerAnneeAcademiqueUseCase;
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

@Service
@Transactional
public class CreerAnneeAcademiqueService
        implements CreerAnneeAcademiqueUseCase {

    private final AnneeAcademiqueRepository repository;
    private final DomainEventPublisher eventPublisher;


    public CreerAnneeAcademiqueService(
            AnneeAcademiqueRepository repository, DomainEventPublisher eventPublisher
    ) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    @Override
    public void executer(CreerAnneeAcademiqueCommand cmd) {

        AnneeAcademiqueId id =
                new AnneeAcademiqueId(cmd.codeAnnee());

        if (repository.findByCode(id.value()).isPresent()) {
            throw new ScolariteNotFoundException(
                    "Année académique déjà existante"
            );
        }

        AnneeAcademique annee =
                new AnneeAcademique(id);
        DatesAnnee datesAnnee = new DatesAnnee(cmd.dateDebut(),
                cmd.dateFin(),
                cmd.dateOuvertureInscription(),
                cmd.dateFinInscription());
        annee.creer(datesAnnee);

        repository.save(annee);

        annee.pullDomainEvents()
                .forEach(eventPublisher::publish);
    }
}

