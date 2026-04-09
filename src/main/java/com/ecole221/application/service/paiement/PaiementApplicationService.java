package com.ecole221.application.service.paiement;


import com.ecole221.application.command.paiement.DeclarerPaiementCommand;
import com.ecole221.application.mapper.PaiementSnapshotMapper;
import com.ecole221.application.port.out.repository.AnneeAcademiqueRepository;
import com.ecole221.application.port.out.repository.ClasseRepository;
import com.ecole221.application.port.out.repository.InscriptionRepository;
import com.ecole221.application.port.out.repository.PaiementRepository;
import com.ecole221.domain.entity.academic.MoisAcademique;
import com.ecole221.domain.entity.inscription.Inscription;
import com.ecole221.domain.entity.inscription.InscriptionCreationSnapshot;
import com.ecole221.domain.entity.paiement.Paiement;
import com.ecole221.domain.entity.paiement.StatutPaiement;
import com.ecole221.domain.entity.school.Classe;
import com.ecole221.domain.entity.school.CodeClasse;
import com.ecole221.domain.exception.ScolariteException;
import com.ecole221.domain.result.paiement.PaiementProcessingResult;
import com.ecole221.domain.service.paiement.PaiementMensualiteProcessor;
import com.ecole221.domain.service.paiement.PlanPaiementInitializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
//@Transactional
public class PaiementApplicationService {

    private final PaiementRepository paiementRepository;
    private final ClasseRepository classeRepository;
    private final AnneeAcademiqueRepository anneeAcademiqueRepository;
    private final PaiementSnapshotMapper paiementSnapshotMapper;
    private final InscriptionRepository inscriptionRepository;
    private final PaiementMensualiteProcessor paiementMensualiteProcessor;
    private final PlanPaiementInitializer planPaiementInitializer;

    public PaiementApplicationService(
            PaiementRepository paiementRepository,
            ClasseRepository classeRepository,
            AnneeAcademiqueRepository anneeAcademiqueRepository,
            PaiementSnapshotMapper paiementSnapshotMapper,
            InscriptionRepository inscriptionRepository,
            PaiementMensualiteProcessor paiementMensualiteProcessor,
            PlanPaiementInitializer planPaiementInitializer
    ) {
        this.paiementRepository = paiementRepository;
        this.classeRepository = classeRepository;
        this.anneeAcademiqueRepository = anneeAcademiqueRepository;
        this.paiementSnapshotMapper = paiementSnapshotMapper;
        this.inscriptionRepository = inscriptionRepository;
        this.paiementMensualiteProcessor = paiementMensualiteProcessor;
        this.planPaiementInitializer = planPaiementInitializer;
    }

    /* ============================================================
       1️⃣ INITIALISATION DU PLAN ANNUEL (appelé à l'inscription)
       ============================================================ */

    //@Transactional(propagation = Propagation.REQUIRES_NEW)
    public PaiementProcessingResult initialiserPlanPaiementAnnuel(InscriptionCreationSnapshot snapshot) {
        try {
            DeclarerPaiementCommand cmd =
                    paiementSnapshotMapper.toDeclarerPaiementCommand(
                            snapshot.paiementSnapshot()
                    );
            Classe classe = chargerClasse(snapshot.codeClasse());
            List<MoisAcademique> moisTries = chargerMoisTries(snapshot);
            PaiementProcessingResult result = planPaiementInitializer.initialiser(
                    snapshot,
                    cmd,
                    classe,
                    moisTries
            );
            paiementRepository.saveAll(result.paiements(), result.contexts());
            return result;
        } catch (Exception e) {
            throw new ScolariteException(e.getMessage());
        }
    }

    /* ============================================================
       2️⃣ PAIEMENT MENSUEL ISOLÉ
       ============================================================ */

    public void payerMensualite(
            DeclarerPaiementCommand cmd,
            String nomFichier
    ) {
        Inscription inscription = inscriptionRepository
                .findByMatriculeAndAnnee(
                        cmd.matricule(),
                        cmd.anneeAcademique()
                )
                .orElseThrow(() ->
                        new ScolariteException("Inscription introuvable")
                );
        List<Paiement> paiements = paiementRepository
                .findMensualitesImpayees(
                        cmd.matricule(),
                        cmd.anneeAcademique()
                );
        if (paiements.isEmpty()) {
            throw new ScolariteException("Aucune mensualité en attente");
        }

        paiements = paiements.stream()
                .sorted(
                        Comparator
                                .comparing((Paiement p) ->
                                        p.getStatut() != StatutPaiement.AVANCE
                                )
                )
                .toList();

        Classe classe = chargerClasse(inscription.getCodeClasse());

        PaiementProcessingResult result = paiementMensualiteProcessor.process(
                paiements,
                classe,
                cmd,
                nomFichier
        );

        paiementRepository.saveAll(result.paiements(), result.contexts());
    }

    /* ============================================================
       MÉTHODES PRIVÉES
       ============================================================ */

    private Classe chargerClasse(CodeClasse codeClasse) {
        return classeRepository
                .findById(codeClasse)
                .orElseThrow(() ->
                        new ScolariteException("Classe introuvable")
                );
    }

    private List<MoisAcademique> chargerMoisTries(
            InscriptionCreationSnapshot snapshot
    ) {
        String codeAnnee =
                snapshot.inscriptionId()
                        .getAnneeAcademiqueId()
                        .value();

        return anneeAcademiqueRepository
                .findByCode(codeAnnee)
                .map(annee ->
                        annee.moisAcademiques()
                                .stream()
                                .sorted(
                                        Comparator
                                                .comparing(MoisAcademique::annee)
                                                .thenComparing(MoisAcademique::mois)
                                )
                                .toList()
                )
                .orElseThrow(() ->
                        new ScolariteException("Mois introuvables")
                );
    }
}
