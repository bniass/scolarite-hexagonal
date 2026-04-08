package com.ecole221.application.usecase.paiement;

import com.ecole221.application.mapper.PaiementSnapshotMapper;
import com.ecole221.application.port.in.paiement.ConsulterPaiementsUseCase;
import com.ecole221.application.port.out.repository.InscriptionRepository;
import com.ecole221.application.port.out.repository.PaiementRepository;
import com.ecole221.application.query.paiement.ConsulterPaiementQuery;
import com.ecole221.application.query.paiement.PaiementView;
import com.ecole221.domain.entity.inscription.Inscription;
import com.ecole221.domain.entity.paiement.Paiement;
import com.ecole221.domain.exception.ScolariteNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ConsulterPaiementsService implements ConsulterPaiementsUseCase {
    private final InscriptionRepository inscriptionRepository;
    private final PaiementRepository paiementRepository;
    private final PaiementSnapshotMapper paiementSnapshotMapper;

    @Override
    public List<PaiementView> execute(ConsulterPaiementQuery consulterPaiementQuery) {
        // verfier s'il est inscrit
        inscriptionRepository.findByMatriculeAndAnnee(
                consulterPaiementQuery.matricule(),
                consulterPaiementQuery.anneeAcademqiue()
        ).orElseThrow(()-> new ScolariteNotFoundException("Inscription introuvable"));


        // recupérer les paiements
        return paiementRepository.findByMatriculeAndAnneeAcademiqueOrdered(
                consulterPaiementQuery.matricule(),
                consulterPaiementQuery.anneeAcademqiue()
        ).stream().map(paiementSnapshotMapper::PaiementToPaiementView).toList();

    }
}
