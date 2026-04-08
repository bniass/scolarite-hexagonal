package com.ecole221.infrastructure.projection.inscription.handler;

import com.ecole221.domain.event.anneeacademic.AnneeAcademiqueCreeeEvent;
import com.ecole221.domain.event.anneeacademic.AnneeAcademiqueModifieEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class AnneeAcademiqueModifierHandler {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(AnneeAcademiqueModifieEvent event) {
        System.out.println("Oui");
    }

}
