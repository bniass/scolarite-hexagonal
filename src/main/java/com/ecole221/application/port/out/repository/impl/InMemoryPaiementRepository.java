package com.ecole221.application.port.out.repository.impl;

import com.ecole221.application.command.paiement.DeclarerPaiementCommand;
import com.ecole221.application.port.out.repository.PaiementRepository;
import com.ecole221.domain.entity.academic.AnneeAcademiqueId;
import com.ecole221.domain.entity.inscription.InscriptionId;
import com.ecole221.domain.entity.paiement.Paiement;
import com.ecole221.domain.entity.paiement.PaiementId;
import com.ecole221.domain.entity.paiement.StatutPaiement;
import com.ecole221.domain.entity.paiement.TypePaiement;
import com.ecole221.domain.entity.school.Montant;
import com.ecole221.domain.entity.student.Matricule;
import com.ecole221.infrastructure.persistence.paiement.adapter.PaiementPersistenceContext;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InMemoryPaiementRepository implements PaiementRepository {

    private final Map<PaiementId, Paiement> store = new HashMap<>();

    @Override
    public Paiement save(Paiement paiement, PaiementPersistenceContext paiementPersistenceContext) {
        store.put(paiement.getId(), paiement);
        return paiement;
    }

    @Override
    public Paiement findById(PaiementId id) {
        return store.get(id);
    }

    @Override
    public List<Paiement> findByMatriculeAndAnneeAcademique(String matricule, String anneeAcademique) {
        return store.values().stream()
                .filter(i ->
                        i.getInscriptionId().getMatricule().value().equals(matricule)
                        && i.getInscriptionId().getAnneeAcademiqueId().value().equals(anneeAcademique)
                )
                .toList();
    }

    @Override
    public List<Paiement> findMensualitesImpayees(String matricule, String anneeAcademique) {
        return store.values().stream()
                .filter(i ->
                        i.getInscriptionId().getMatricule().value().equals(matricule)
                        && i.getInscriptionId().getAnneeAcademiqueId().value().equals(anneeAcademique)
                        && i.getStatut().equals(StatutPaiement.PAYE)
                ).toList();
    }

    @Override
    public List<Paiement> findByMatriculeAndAnneeAcademiqueOrdered(String matricule, String anneeAcademique) {
        return List.of();
    }

    @Override
    public List<Paiement> findByIdIn(List<UUID> paiements) {
        return List.of();
    }

}
