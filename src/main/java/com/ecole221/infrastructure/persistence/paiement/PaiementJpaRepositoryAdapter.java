package com.ecole221.infrastructure.persistence.paiement;

import com.ecole221.domain.entity.paiement.Paiement;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class PaiementJpaRepositoryAdapter /*implements PaiementRepository */ {

    //private final PaiementJpaRepository jpaRepository;
    //private final OutboxEventJpaRepository outboxRepository;

    //@Override
    public Paiement save(Paiement paiement) {

        // 1. Persister l’aggregate
        //PaiementJpaEntity entity = mapper.toEntity(paiement);
        //jpaRepository.save(entity);

        // 2. Extraire les DomainEvents
        paiement.pullDomainEvents()
                .forEach(event -> {
                    //outboxRepository.save(
                            //OutboxEventJpaEntity.from(event)
                    //);
                });

        return paiement;
    }
}

