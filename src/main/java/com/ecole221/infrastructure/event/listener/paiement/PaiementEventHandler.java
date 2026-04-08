package com.ecole221.infrastructure.event.listener.paiement;

import com.ecole221.application.command.iscription.CreerInscriptionCommand;
import com.ecole221.application.command.paiement.DeclarerPaiementCommand;
import com.ecole221.application.factory.paiement.PaiementFactory;
import com.ecole221.application.idempotence.IdempotenceService;
import com.ecole221.application.mapper.PaiementSnapshotMapper;
import com.ecole221.application.port.out.repository.AnneeAcademiqueRepository;
import com.ecole221.application.port.out.repository.ClasseRepository;
import com.ecole221.application.port.out.repository.PaiementRepository;
import com.ecole221.domain.entity.academic.MoisAcademique;
import com.ecole221.domain.entity.paiement.CanalPaiement;
import com.ecole221.domain.entity.paiement.Paiement;
import com.ecole221.domain.entity.paiement.PaiementSnapshot;
import com.ecole221.domain.entity.paiement.StatutPaiement;
import com.ecole221.domain.entity.school.Classe;
import com.ecole221.domain.entity.school.Montant;
import com.ecole221.domain.event.anneeacademic.AnneeAcademiqueCreeeEvent;
import com.ecole221.domain.event.inscription.InscriptionCreeeEvent;
import com.ecole221.domain.exception.ScolariteException;
import com.ecole221.infrastructure.persistence.paiement.adapter.PaiementPersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Component
public class PaiementEventHandler {

    private final IdempotenceService idempotenceService;
    private final PaiementRepository paiementRepository;
    private final ClasseRepository classeRepository;
    private final AnneeAcademiqueRepository anneeAcademiqueRepository;
    private final PaiementSnapshotMapper paiementSnapshotMapper;


    public PaiementEventHandler(
            IdempotenceService idempotenceService,
            PaiementRepository paiementRepository, ClasseRepository classeRepository, AnneeAcademiqueRepository anneeAcademiqueRepository, PaiementSnapshotMapper paiementSnapshotMapper
    ) {
        this.idempotenceService = idempotenceService;
        this.paiementRepository = paiementRepository;
        this.classeRepository = classeRepository;
        this.anneeAcademiqueRepository = anneeAcademiqueRepository;
        this.paiementSnapshotMapper = paiementSnapshotMapper;
    }

    @Retryable(
            retryFor = { DeadlockLoserDataAccessException.class, SQLException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 2)
    )
    //@Transactional(propagation = Propagation.REQUIRES_NEW)
    //@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void onAnneeAcademiqueCreee(InscriptionCreeeEvent event) {


        //String eventId = event.getInscriptionId().getAnneeAcademiqueId().value();

        //if (idempotenceService.alreadyProcessed(eventId)) {
            //return;
        //}

//        DeclarerPaiementCommand cmd = paiementSnapshotMapper
//                .toDeclarerPaiementCommand(event.getInscriptionCreationSnapshot().paiementSnapshot());
//
//        if(cmd.nombreDeMois() > 9){
//            throw new ScolariteException("Le nombre de mois maximum est de 9 mois");
//        }
//
//        // créer les futures paiement
//        //System.out.println("Futurs paiements ici !");
//        Classe classe = classeRepository.findById(event.getInscriptionCreationSnapshot().codeClasse())
//                .orElseThrow(() -> new ScolariteException("Classe introuvable"));
//
//        Montant montantAPeyer = classe.getFraisInscription()
//                .additionner(classe.getAutresFrais())
//                .additionner(new Montant(classe.getMensualite().valeur().multiply(BigDecimal.valueOf(cmd.nombreDeMois()))));
//
//        int dif = montantAPeyer.valeur().subtract(new BigDecimal(cmd.montantPaye())).intValue();
//        if(dif != 0){
//            throw new ScolariteException("Le montant à payer doit être egal à : "+montantAPeyer);
//        }
//        String code = event.getInscriptionId()
//                .getAnneeAcademiqueId()
//                .value();
//        //récupérer les mois scolaires
//        List<MoisAcademique> moisTries =
//                anneeAcademiqueRepository
//                        .findByCode(code)
//                        .map(anneeAcademique ->
//                                anneeAcademique.moisAcademiques()
//                                        .stream()
//                                        .sorted(
//                                                Comparator
//                                                        .comparing(MoisAcademique::annee)
//                                                        .thenComparing(MoisAcademique::mois)
//                                        )
//                                        .toList()
//                        )
//                        .orElseThrow(() ->
//                                new ScolariteException(
//                                        "Mois de l'année scolaire introuvables !"
//                                )
//                        );
//        int nbPayes = cmd.nombreDeMois();
//
//        MoisAcademique dernierMois = moisTries.get(moisTries.size() - 1);
//
//        // mois payés à l'inscription
//        List<MoisAcademique> moisPayes = new ArrayList<>();
//
//        // 1️⃣ les N-1 premiers mois
//        moisPayes.addAll(moisTries.subList(0, nbPayes - 1));
//
//        // 2️⃣ le dernier mois (obligatoire)
//        moisPayes.add(dernierMois);
//
//        // mois impayés = le reste
//        List<MoisAcademique> moisImpayes =
//                moisTries.stream()
//                        .filter(m -> !moisPayes.contains(m))
//                        .toList();
//
//        //enrégistrement des mensualités payées à l'inscription
//        List<Paiement> paiements = new ArrayList<>();
//        List<PaiementPersistenceContext> contexts = new ArrayList<>();
//
//        Paiement paiementFraisInscription = PaiementFactory.creerPaiementFraisInscription(cmd);
//
//        PaiementPersistenceContext contextFI = PaiementFactory
//                .creerPaiementPersistenceContexte(cmd, paiementFraisInscription.getId().value(), classe.getFraisInscription().valeur().intValue());
//        paiementFraisInscription.attribuerStatut(StatutPaiement.PAYE);
//        paiements.add(paiementFraisInscription);
//        contexts.add(contextFI);
//
//        Paiement paiementFraisDivers = PaiementFactory.creerPaiementFraisDivers(cmd);
//        PaiementPersistenceContext contextFD = PaiementFactory
//                .creerPaiementPersistenceContexte(cmd, paiementFraisInscription.getId().value(), classe.getAutresFrais().valeur().intValue());
//        paiementFraisDivers.attribuerStatut(StatutPaiement.PAYE);
//        paiements.add(paiementFraisDivers);
//        contexts.add(contextFD);
//
//        for (int i = 0; i < moisPayes.size() - 1; i++) {
//            Paiement paiement = PaiementFactory.creerPaiementMensualite(cmd, moisPayes.get(i));
//            PaiementPersistenceContext contextP = PaiementFactory
//                    .creerPaiementPersistenceContexte(cmd, paiement.getId().value(), classe.getMensualite().valeur().intValue());
//            paiements.add(paiement);
//            paiement.attribuerStatut(StatutPaiement.PAYE);
//            contexts.add(contextP);
//        }
//
//        //initialisation des prochains paiements
//        for (MoisAcademique mois : moisImpayes) {
//            Paiement paiement = PaiementFactory.creerPaiementMensualiteInitial(event.getInscriptionId(), mois);
//            DeclarerPaiementCommand declarerPaiementCommand = new DeclarerPaiementCommand(
//                    cmd.matricule(), cmd.anneeAcademique(), cmd.anneeAcademique(),
//                    CanalPaiement.NOT_YET_PAIED, "", "",
//                    "", "", 0.0, null,
//                    null, cmd.preuvePaiement(), cmd.nombreDeMois()
//            );
//            paiement.attribuerStatut(StatutPaiement.IMPAYE);
//            PaiementPersistenceContext contextImp = PaiementFactory
//                    .creerPaiementPersistenceContexte(declarerPaiementCommand, paiementFraisInscription.getId().value(), classe.getMensualite().valeur().intValue());
//            paiements.add(paiement);
//            contexts.add(contextImp);
//        }
//        Paiement paiementDernierMois = PaiementFactory.creerPaiementMensualite(cmd, moisPayes.get(moisPayes.size() - 1));
//        PaiementPersistenceContext contextDernierMois = PaiementFactory
//                .creerPaiementPersistenceContexte(cmd, paiementDernierMois.getId().value(), classe.getMensualite().valeur().intValue());
//        paiementDernierMois.attribuerStatut(StatutPaiement.PAYE);
//        paiements.add(paiementDernierMois);
//        contexts.add(contextDernierMois);
//        paiementRepository.saveAll(paiements, contexts);
    }

    @Recover
    public void recover(Exception ex, InscriptionCreeeEvent event) {
        // fallback final après tous les retry
        log.error("Échec définitif pour l'événement {}", event.getInscriptionId().getMatricule().value(), ex);

        // 1️⃣ table d'erreur
        // 2️⃣ DLQ
        // 3️⃣ alerte
    }
}

