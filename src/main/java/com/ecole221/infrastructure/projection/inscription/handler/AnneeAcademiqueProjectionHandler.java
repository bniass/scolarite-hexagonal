package com.ecole221.infrastructure.projection.inscription.handler;

import com.ecole221.domain.event.anneeacademic.AnneeAcademiqueCreeeEvent;
import com.ecole221.domain.event.anneeacademic.InscriptionsFermeesEvent;
import com.ecole221.domain.event.anneeacademic.InscriptionsOuvertesEvent;
import com.ecole221.infrastructure.projection.inscription.entity.AnneeAcademiqueProjection;
import com.ecole221.infrastructure.projection.inscription.repository.AnneeAcademiqueProjectionRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

@Component
public class AnneeAcademiqueProjectionHandler {

    private final AnneeAcademiqueProjectionRepository repository;

    public AnneeAcademiqueProjectionHandler(AnneeAcademiqueProjectionRepository repository) {
        this.repository = repository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(AnneeAcademiqueCreeeEvent event) {
        AnneeAcademiqueProjection projection =
                repository.findById(event.aggregateId())
                        .orElse(new AnneeAcademiqueProjection(event.aggregateId()));

        projection.initialiserDateInscription(
                event.getAnnee().getDateOuvertureInscription(),
                event.getAnnee().getDateFinInscription()
        );
        repository.save(projection);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(InscriptionsOuvertesEvent event) {
        AnneeAcademiqueProjection projection =
                repository.findById(event.aggregateId())
                        .orElse(new AnneeAcademiqueProjection(event.aggregateId()));

        projection.ouvrir();
        repository.save(projection);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(InscriptionsFermeesEvent event) {
        AnneeAcademiqueProjection projection =
                repository.findById(event.aggregateId())
                        .orElse(new AnneeAcademiqueProjection(event.aggregateId()));
        projection.fermer();
        repository.save(projection);
    }
}
