package com.ecole221.application.port.out.repository;


import com.ecole221.application.result.paiement.PaiementResult;
import com.ecole221.domain.entity.paiement.Paiement;
import com.ecole221.domain.entity.paiement.PaiementId;
import com.ecole221.domain.entity.paiement.PaiementSnapshot;
import com.ecole221.infrastructure.persistence.paiement.adapter.PaiementPersistenceContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public interface PaiementRepository {

    Paiement save(Paiement paiement, PaiementPersistenceContext paiementPersistenceContext);

    default void saveAll(
            List<Paiement> paiements,
            List<PaiementPersistenceContext> contexts
    ) {
        try {
            AtomicInteger i = new AtomicInteger();
            paiements.forEach(p -> {
                save(p, contexts.get(i.get()));
                i.getAndIncrement();
            });
        } catch (Exception e) {
            throw e;
        }
    }

    Paiement findById(PaiementId id);

    List<Paiement> findByMatriculeAndAnneeAcademique(
            String matricule,
            String anneeAcademique
    );

    public List<Paiement> findMensualitesImpayees(
            String matricule,
            String anneeAcademique
    );

    List<Paiement> findByMatriculeAndAnneeAcademiqueOrdered(
            String matricule,
            String anneeAcademique
    );

    List<Paiement> findByIdIn(List<UUID> paiements);
}

