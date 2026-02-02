package com.ecole221.application.usecase.inscription;

import com.ecole221.application.command.iscription.CreerInscriptionCommand;
import com.ecole221.application.port.in.inscription.CreerInscriptionUseCase;
import com.ecole221.application.port.out.repository.AnneeAcademiqueRepository;
import com.ecole221.application.port.out.repository.ClasseRepository;
import com.ecole221.application.port.out.repository.EtudiantRepository;
import com.ecole221.application.port.out.repository.InscriptionRepository;
import com.ecole221.domain.entity.academic.AnneeAcademique;
import com.ecole221.domain.entity.inscription.Inscription;
import com.ecole221.domain.entity.inscription.InscriptionId;
import com.ecole221.domain.entity.school.Classe;
import com.ecole221.domain.entity.school.CodeClasse;
import com.ecole221.domain.entity.student.Etudiant;
import com.ecole221.domain.entity.student.Matricule;
import com.ecole221.domain.exception.ScolariteException;
import com.ecole221.domain.policy.InscriptionProcessPolicy;
import com.ecole221.infrastructure.projection.inscription.repository.AnneeAcademiqueProjectionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class CreerInscriptionService implements CreerInscriptionUseCase {

    private final ClasseRepository classeRepository;
    private final InscriptionRepository inscriptionRepository;
    private final AnneeAcademiqueRepository anneeAcademiqueRepository;
    private final EtudiantRepository etudiantRepository;
    private final AnneeAcademiqueProjectionRepository etatInscriptionOuverteRepository;
    public CreerInscriptionService(
            ClasseRepository classeRepository,
            InscriptionRepository inscriptionRepository, AnneeAcademiqueRepository anneeAcademiqueRepository, EtudiantRepository etudiantRepository, AnneeAcademiqueProjectionRepository etatInscriptionOuverteRepository
    ) {
        this.classeRepository = classeRepository;
        this.inscriptionRepository = inscriptionRepository;
        this.anneeAcademiqueRepository = anneeAcademiqueRepository;
        this.etudiantRepository = etudiantRepository;
        this.etatInscriptionOuverteRepository = etatInscriptionOuverteRepository;
    }

    @Override
    public void executer(CreerInscriptionCommand cmd) {
        Optional<AnneeAcademique> annee = anneeAcademiqueRepository.findByCode(cmd.anneeAcademique());
        if(!annee.isPresent()){
            throw new ScolariteException("Année scolaire introuvable!");
        }

        Classe classe = classeRepository.findById(new CodeClasse(cmd.codeClasse()))
                .orElseThrow(() -> new IllegalStateException("Classe introuvable"));


        InscriptionId inscriptionId = new InscriptionId(new Matricule(cmd.matricule()),
                annee.get());

        Inscription inscription =
                new Inscription(
                        inscriptionId,
                        classe,
                        LocalDate.now()
                );
        InscriptionProcessPolicy processService = new InscriptionProcessPolicy();
        // verifier si inscription ouverte avant d'inscrire l'étudiant
        processService.verifierEtCreerInscription(annee.get());
        // vérifier s'il n'est pas déjà inscrit pour l'anné académique
        inscription.creerNouvelle(inscription.getId(), inscription.classeCourante());

        if (!etatInscriptionOuverteRepository
                .existsByAnneeAcademiqueCodeAndInscriptionsOuvertesTrue(
                        cmd.anneeAcademique()
                )) {
            throw new ScolariteException(
                    "Les inscriptions ne sont pas ouvertes pour cette année académique"
            );
        }


        Optional<Inscription> oldInscription =
                inscriptionRepository.findByMatriculeAndAnnee(
                        cmd.matricule(),
                        cmd.anneeAcademique()
                );

        if (oldInscription.isPresent()) {
            throw new ScolariteException(
                    "Inscription déjà existante pour cette année académique"
            );
        }
;        // Créer l’étudiant s’il n’existe pas
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
        inscriptionRepository.save(inscription);
    }
}
