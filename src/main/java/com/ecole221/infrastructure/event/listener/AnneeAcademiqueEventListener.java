package com.ecole221.infrastructure.event.listener;

import com.ecole221.domain.event.anneeacademic.AnneeAcademiqueCreeeEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

@Component
public class AnneeAcademiqueEventListener {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onAnneeAcademiqueCreee(AnneeAcademiqueCreeeEvent event) {
        System.out.println(event.getAnnee().getId().value()+ " occured at "+event.occurredAt());
        // log
        // notification
        // audit
        // projection read-model
    }
}

