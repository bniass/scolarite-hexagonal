package com.ecole221.application.usecase.inscription;

import com.ecole221.application.command.iscription.CreerInscriptionCommand;
import com.ecole221.application.mapper.PaiementSnapshotMapper;
import com.ecole221.application.port.in.inscription.CreerInscriptionUseCase;
import com.ecole221.application.port.out.event.DomainEventPublisher;
import com.ecole221.application.port.out.repository.AnneeAcademiqueRepository;
import com.ecole221.application.port.out.repository.ClasseRepository;
import com.ecole221.application.port.out.repository.EtudiantRepository;
import com.ecole221.application.port.out.repository.InscriptionRepository;
import com.ecole221.application.service.paiement.PaiementApplicationService;
import com.ecole221.domain.entity.academic.AnneeAcademique;
import com.ecole221.domain.entity.inscription.Inscription;
import com.ecole221.domain.entity.inscription.InscriptionCreationSnapshot;
import com.ecole221.domain.entity.inscription.InscriptionId;
import com.ecole221.domain.entity.paiement.PaiementSnapshot;
import com.ecole221.domain.entity.school.Classe;
import com.ecole221.domain.entity.school.CodeClasse;
import com.ecole221.domain.entity.student.Etudiant;
import com.ecole221.domain.entity.student.Matricule;
import com.ecole221.domain.exception.ScolariteException;
import com.ecole221.domain.policy.inscription.InscriptionProcessPolicy;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@Transactional
public class CreerInscriptionService implements CreerInscriptionUseCase {

    private final ClasseRepository classeRepository;
    private final InscriptionRepository inscriptionRepository;
    private final AnneeAcademiqueRepository anneeAcademiqueRepository;
    private final EtudiantRepository etudiantRepository;
    private final DomainEventPublisher eventPublisher;
    private final InscriptionProcessPolicy inscriptionPolicy;
    private final PaiementSnapshotMapper paiementSnapshotMapper;
    private final PaiementApplicationService paiementApplicationService;

    public CreerInscriptionService(
            ClasseRepository classeRepository,
            InscriptionRepository inscriptionRepository,
            AnneeAcademiqueRepository anneeAcademiqueRepository,
            EtudiantRepository etudiantRepository,
            DomainEventPublisher eventPublisher,
            InscriptionProcessPolicy inscriptionPolicy, PaiementSnapshotMapper paiementSnapshotMapper, PaiementApplicationService paiementApplicationService1
    ) {
        this.classeRepository = classeRepository;
        this.inscriptionRepository = inscriptionRepository;
        this.anneeAcademiqueRepository = anneeAcademiqueRepository;
        this.etudiantRepository = etudiantRepository;
        this.eventPublisher = eventPublisher;
        this.inscriptionPolicy = inscriptionPolicy;
        this.paiementSnapshotMapper = paiementSnapshotMapper;
        this.paiementApplicationService = paiementApplicationService1;
    }

    @Transactional
    @Override
    public void executer(CreerInscriptionCommand cmd, MultipartFile preuvePaiement) {

        // 1️⃣ Charger l’année académique
        AnneeAcademique annee = anneeAcademiqueRepository
                .findByCode(cmd.anneeAcademique())
                .orElseThrow(() -> new ScolariteException("Année académique introuvable"));

        // 2️⃣ Vérifier que les inscriptions sont ouvertes (règle métier)
        inscriptionPolicy.verifierInscriptionAutorisee(annee);

        // 3️⃣ Vérifier l’unicité de l’inscription
        if (inscriptionRepository.existsByMatriculeAndAnnee(
                cmd.matricule(),
                cmd.anneeAcademique()
        )) {
            throw new ScolariteException(
                    "Inscription déjà existante pour cette année académique"
            );
        }

        // 4️⃣ Charger la classe
        Classe classe = classeRepository
                .findById(new CodeClasse(cmd.codeClasse()))
                .orElseThrow(() -> new ScolariteException("Classe introuvable"));


        // 5️⃣ Créer l’étudiant si nécessaire
        if (!etudiantRepository.existsByMatricule(cmd.matricule())) {
            if (cmd.nom() == null || cmd.prenom() == null || cmd.dateNaissance() == null) {
                throw new ScolariteException(
                        "Données étudiant obligatoires pour une première inscription"
                );
            }
            Etudiant etudiant = Etudiant.creer(
                    cmd.matricule(),
                    cmd.nom(),
                    cmd.prenom(),
                    cmd.dateNaissance()
            );
            etudiantRepository.save(etudiant);
        }
        InscriptionId inscriptionId = new InscriptionId(
                new Matricule(cmd.matricule()),
                annee.getId()
        );

        String nomFichier = "";

        // Traitement image
        if (!preuvePaiement.isEmpty()) {
            nomFichier = preuvePaiement.getOriginalFilename();
            // sauvegarder fichier

        }

        PaiementSnapshot paiementSnapshot = paiementSnapshotMapper
                .toPaiementSnapshot(cmd, nomFichier);

        String sagaId = UUID.randomUUID().toString();
        InscriptionCreationSnapshot creationSnapshot = new InscriptionCreationSnapshot(
                inscriptionId,
                classe.getCode(),
                paiementSnapshot,
                null,
                sagaId
        );

        // 6️⃣ Créer l’inscription via la factory métier
        Inscription inscription = Inscription.creerNouvelle(creationSnapshot);

        // 7️⃣ Persister
        inscriptionRepository.save(inscription);
        //gérer dans le saga
        //paiementApplicationService.initialiserPlanPaiementAnnuel(creationSnapshot);

        // 8️⃣ Publier les événements de domaine
        inscription.pullDomainEvents().forEach(eventPublisher::publish);
    }
}
