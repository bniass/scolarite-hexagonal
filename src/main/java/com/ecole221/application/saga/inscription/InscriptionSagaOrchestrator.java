package com.ecole221.application.saga.inscription;

import com.ecole221.application.port.in.inscription.AnnulerInscriptionUseCase;
import com.ecole221.application.port.out.event.DomainEventPublisher;
import com.ecole221.application.service.paiement.PaiementApplicationService;
import com.ecole221.domain.event.inscription.InscriptionCreeeEvent;
import com.ecole221.domain.event.paiement.PaiementInitialEchoueEvent;
import com.ecole221.domain.event.paiement.PaiementInitialValideEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class InscriptionSagaOrchestrator {

    private final PaiementApplicationService paiementApplicationService;
    private final DomainEventPublisher eventPublisher;
    private final AnnulerInscriptionUseCase annulerInscriptionUseCase;

    public InscriptionSagaOrchestrator(
            PaiementApplicationService paiementApplicationService,
            DomainEventPublisher eventPublisher, AnnulerInscriptionUseCase annulerInscriptionUseCase
    ) {
        this.paiementApplicationService = paiementApplicationService;
        this.eventPublisher = eventPublisher;
        this.annulerInscriptionUseCase = annulerInscriptionUseCase;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onInscriptionCreee(InscriptionCreeeEvent event) {
        try {
            paiementApplicationService.initialiserPlanPaiementAnnuel(
                    event.getInscriptionCreationSnapshot()
            );

            eventPublisher.publish(
                    new PaiementInitialValideEvent(
                            event.getInscriptionId(),
                            event.getSagaId()
                    )
            );
        } catch (Exception ex) {
            eventPublisher.publish(
                    new PaiementInitialEchoueEvent(
                            event.getInscriptionId(),
                            event.getSagaId(),
                            ex.getMessage()
                    )
            );
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onPaiementEchoue(PaiementInitialEchoueEvent event) {

        String matricule = event.getInscriptionId().getMatricule().value();
        String annee = event.getInscriptionId().getAnneeAcademiqueId().value();

        annulerInscriptionUseCase.annuler(
                matricule,
                annee,
                "Paiement initial échoué (SagaId=" + event.getSagaId() + ")"
        );
    }
}