package com.ecole221.infrastructure.persistence.paiement.adapter;

import com.ecole221.application.command.paiement.DeclarerPaiementCommand;
import com.ecole221.application.port.out.repository.PaiementRepository;
import com.ecole221.domain.entity.paiement.Paiement;
import com.ecole221.domain.entity.paiement.PaiementId;
import com.ecole221.domain.entity.paiement.StatutPaiement;
import com.ecole221.domain.entity.paiement.TypePaiement;
import com.ecole221.domain.exception.ScolariteException;
import com.ecole221.infrastructure.persistence.anneeacademique.entity.AnneeMoisJpaEntity;
import com.ecole221.infrastructure.persistence.paiement.entity.PaiementJpaEntity;
import com.ecole221.infrastructure.persistence.paiement.entity.StatutJpaPaiement;
import com.ecole221.infrastructure.persistence.paiement.mapper.PaiementPersistenceMapper;
import com.ecole221.infrastructure.persistence.paiement.repository.PaiementJpaRepository;
import com.ecole221.infrastructure.web.paiement.PaiementStatutMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Transactional
public class PaiementRepositoryMySqlAdapter implements PaiementRepository {

    private final PaiementJpaRepository jpaRepository;
    private final PaiementPersistenceMapper mapper;

    public PaiementRepositoryMySqlAdapter(
            PaiementJpaRepository jpaRepository,
            PaiementPersistenceMapper mapper
    ) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public Paiement save(Paiement paiement, PaiementPersistenceContext paiementPersistenceContext) {
        PaiementJpaEntity entity = mapper.fromPaiementEtPaiementPersistenceContext(paiement, paiementPersistenceContext);
        PaiementJpaEntity fromdb = jpaRepository.findById(entity.getId()).orElse(null);
        if(fromdb != null){
            fromdb.copyFrom(entity);
            jpaRepository.save(fromdb);
        }
        else{
            jpaRepository.save(entity);
        }
        return paiement;
    }




    @Override
    public Paiement findById(PaiementId id) {
        Optional<PaiementJpaEntity> entity =
                jpaRepository.findById(id.value());

        return entity
                .map(mapper::toDomain)
                .orElseThrow(() -> new ScolariteException(
                        "Paiement introuvable : " + id.value()
                ));
    }

    @Override
    public List<Paiement> findByMatriculeAndAnneeAcademique(String matricule, String anneeAcademique) {
        return jpaRepository.findByMatriculeAndAnneeAcademique(matricule, anneeAcademique)
                .stream().map(mapper::toDomain).toList();
    }



    @Override
    public List<Paiement> findMensualitesImpayees(
            String matricule,
            String anneeAcademique
    ) {

        return jpaRepository
                .findByMatriculeAndAnneeAcademiqueAndTypePaiementAndStatutInOrderByMoisAcademiqueAnneeAscMoisAcademiqueMoisAsc(
                        matricule,
                        anneeAcademique,
                        TypePaiement.MENSUALITE,
                        List.of(StatutJpaPaiement.IMPAYE, StatutJpaPaiement.AVANCE)
                )
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Paiement> findByMatriculeAndAnneeAcademiqueOrdered(String matricule, String anneeAcademique) {
        return jpaRepository.findByMatriculeAndAnneeAcademiqueOrderByMoisAcademiqueAnneeAscMoisAcademiqueMoisAsc(matricule, anneeAcademique)
                .stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Paiement> findByIdIn(List<UUID> paiements) {
        return jpaRepository.findByIdIn(paiements)
                .stream().map(mapper::toDomain).toList();
    }


}

