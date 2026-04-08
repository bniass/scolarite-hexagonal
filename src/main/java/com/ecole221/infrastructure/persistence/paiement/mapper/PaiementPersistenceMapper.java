package com.ecole221.infrastructure.persistence.paiement.mapper;

import com.ecole221.application.command.paiement.DeclarerPaiementCommand;
import com.ecole221.domain.entity.academic.AnneeAcademiqueId;
import com.ecole221.domain.entity.academic.MoisAcademique;
import com.ecole221.domain.entity.inscription.InscriptionId;
import com.ecole221.domain.entity.paiement.*;
import com.ecole221.domain.entity.school.Montant;
import com.ecole221.domain.entity.student.Matricule;
import com.ecole221.domain.exception.ScolariteException;
import com.ecole221.infrastructure.persistence.anneeacademique.entity.AnneeMoisJpaEntity;
import com.ecole221.infrastructure.persistence.anneeacademique.repository.AnneeMoisJpaRepository;
import com.ecole221.infrastructure.persistence.paiement.adapter.PaiementPersistenceContext;
import com.ecole221.infrastructure.persistence.paiement.entity.PaiementJpaEntity;
import com.ecole221.infrastructure.persistence.paiement.entity.StatutJpaPaiement;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
@Component
public class PaiementPersistenceMapper {
    private final AnneeMoisJpaRepository anneeMoisJpaRepository;

    public PaiementPersistenceMapper(AnneeMoisJpaRepository anneeMoisJpaRepository) {
        this.anneeMoisJpaRepository = anneeMoisJpaRepository;
    }

    /* ==========================================================
       DECLARATION (COMMAND) → JPA
       ========================================================== */

    public PaiementJpaEntity fromPaiementEtPaiementPersistenceContext(
            Paiement paiement, // nullable
            PaiementPersistenceContext pcx
    ) {
        AnneeMoisJpaEntity moisJpa = null;
        if(paiement.getTypePaiement() == TypePaiement.MENSUALITE) {
            MoisAcademique m = paiement.getMoisAcademique();

            moisJpa =
                    anneeMoisJpaRepository
                            .findByAnneeAcademiqueCodeAndMoisAndAnnee(
                                    paiement.getInscriptionId().getAnneeAcademiqueId().value(),
                                    m.mois(),
                                    m.annee()
                            )
                            .orElseThrow(() ->
                                    new ScolariteException(
                                            "Mois académique introuvable : "
                                                    + m.mois() + "-" + m.annee()
                                    )
                            );
        }
        int paye = statutPaiementToJpa(paiement.getStatut()).equals(StatutJpaPaiement.IMPAYE)?0:pcx.totalApapyer(); // => réellement payé
        String commentaires = (paye != 0 && paye < paiement.getMontantPrevu().valeur().intValue())
                ? "Avance de " + paye + " au "+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))+". "
                : (paye != 0 && paye == paiement.getMontantPrevu().valeur().intValue()) ? "Paiement intégral au "+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")):"";
        return new PaiementJpaEntity(
                paiement.getId().value(),
                paiement.getInscriptionId().getMatricule().value(),
                paiement.getInscriptionId().getAnneeAcademiqueId().value(),
                paiement.getTypePaiement(),
                pcx.canalPaiement(),
                pcx.operateurMobileMoney(),
                pcx.referenceMobileMoney(),
                pcx.nomBanque(),
                pcx.referenceBancaire(),
                paiement.getMontantPrevu().valeur().doubleValue(), // mensualité ou FI ou FD => prévu
                paye,
                moisJpa, //new AnneeMoisJpaEntity()moisAcademique,
                statutPaiementToJpa(paiement.getStatut()).equals(StatutJpaPaiement.IMPAYE)?null:paiement.getDatePaiement(),
                statutPaiementToJpa(paiement.getStatut()).equals(StatutJpaPaiement.IMPAYE)?null:pcx.heurePaiement(),
                pcx.urlPreuve(),
                statutPaiementToJpa(paiement.getStatut()),
                commentaires
        );
    }

    /* ==========================================================
       JPA → DOMAINE
       ========================================================== */

    public Paiement toDomain(PaiementJpaEntity entity) {
        String annee = entity.getAnneeAcademique().matches("^\\d+-\\d+$")
                ? entity.getAnneeAcademique().split("-")[0]
                : entity.getAnneeAcademique();
        InscriptionId inscriptionId =
                new InscriptionId(
                        new Matricule(entity.getMatricule()),
                        new AnneeAcademiqueId(Integer.parseInt(annee))
                );

        MoisAcademique moisAcademique = null;
        if (entity.getMoisAcademique() != null) {
            AnneeMoisJpaEntity m = entity.getMoisAcademique();
            moisAcademique = new MoisAcademique(m.getMois(), m.getAnnee());
        }

        return Paiement.reconstituer(
                new PaiementId(entity.getId()),
                inscriptionId,
                entity.getDatePaiement() == null ? LocalDate.now() : entity.getDatePaiement(),
                new Montant(BigDecimal.valueOf(entity.getMontantPaye())),
                new Montant(BigDecimal.valueOf(entity.getMontantPrevu())),
                entity.getTypePaiement(),
                entity.getCanalPaiement(),
                entity.getMoisAcademique() != null?
                new MoisAcademique(
                        entity.getMoisAcademique(). getMois(),
                        entity.getMoisAcademique(). getAnnee()
                ):null,
                statutJpaPaiementToDomain(entity.getStatut())
        );
    }

    public StatutPaiement statutJpaPaiementToDomain(StatutJpaPaiement jpa) {
        if (jpa == null) return null;
        return StatutPaiement.valueOf(jpa.name());
    }

    public StatutJpaPaiement statutPaiementToJpa(StatutPaiement domain) {
        if (domain == null) return null;
        return StatutJpaPaiement.valueOf(domain.name());
    }
}
