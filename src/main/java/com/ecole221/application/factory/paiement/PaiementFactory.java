package com.ecole221.application.factory.paiement;

import com.ecole221.application.command.paiement.DeclarerPaiementCommand;
import com.ecole221.domain.entity.academic.AnneeAcademiqueId;
import com.ecole221.domain.entity.academic.MoisAcademique;
import com.ecole221.domain.entity.inscription.InscriptionId;
import com.ecole221.domain.entity.paiement.*;
import com.ecole221.domain.entity.school.Montant;
import com.ecole221.domain.entity.student.Matricule;
import com.ecole221.infrastructure.persistence.paiement.adapter.PaiementPersistenceContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.UUID;

public final class PaiementFactory {

    private PaiementFactory() {
        // utility class
    }

    public static Paiement creerPaiementFraisInscription(DeclarerPaiementCommand cmd){
        return Paiement.reconstituer(
                new PaiementId(UUID.randomUUID()),
                new InscriptionId(
                        new Matricule(cmd.matricule()),
                        new AnneeAcademiqueId(Integer.parseInt(cmd.anneeAcademique().substring(0, 4)))
                ),
                cmd.datePaiement(),
                new Montant(new BigDecimal(cmd.montantPaye())),
                new Montant(new BigDecimal(cmd.montantPaye())),
                TypePaiement.FRAIS_INSCRIPTION,
                cmd.canalPaiement(),
                null,
                StatutPaiement.PAYE
        );
    }

    public static Paiement creerPaiementFraisDivers(DeclarerPaiementCommand cmd){
        return Paiement.reconstituer(
                new PaiementId(UUID.randomUUID()),
                new InscriptionId(
                        new Matricule(cmd.matricule()),
                        new AnneeAcademiqueId(Integer.parseInt(cmd.anneeAcademique().substring(0, 4)))
                ),
                cmd.datePaiement(),
                new Montant(new BigDecimal(cmd.montantPaye())),
                new Montant(new BigDecimal(cmd.montantPaye())),
                TypePaiement.FRAIS_DIVERS,
                cmd.canalPaiement(),
                null,
                StatutPaiement.PAYE
        );
    }

    public static Paiement creerPaiementMensualite(DeclarerPaiementCommand cmd, MoisAcademique mois){
        return Paiement.reconstituer(
                new PaiementId(UUID.randomUUID()),
                new InscriptionId(
                        new Matricule(cmd.matricule()),
                        new AnneeAcademiqueId(Integer.parseInt(cmd.anneeAcademique().substring(0, 4)))
                ),
                cmd.datePaiement(),
                new Montant(new BigDecimal(cmd.montantPaye())),
                new Montant(new BigDecimal(cmd.montantPaye())),
                TypePaiement.MENSUALITE,
                cmd.canalPaiement(),
                mois,
                StatutPaiement.IMPAYE
        );
    }

    public static Paiement creerPaiementMensualiteInitial(InscriptionId inscriptionId, MoisAcademique mois, CanalPaiement canalPaiement){
        return Paiement.reconstituer(
                new PaiementId(UUID.randomUUID()),
                new InscriptionId(
                        new Matricule(inscriptionId.getMatricule().value()),
                        new AnneeAcademiqueId(inscriptionId.getAnneeAcademiqueId().debut())
                ),
                LocalDate.now(),
                new Montant(BigDecimal.ZERO),
                new Montant(BigDecimal.ZERO),
                TypePaiement.MENSUALITE,
                canalPaiement,
                mois,
                StatutPaiement.IMPAYE
        );
    }

    public static PaiementPersistenceContext creerPaiementPersistenceContexte(DeclarerPaiementCommand cmd, UUID id, int totalAppayer){
        return new PaiementPersistenceContext(
                id,
                totalAppayer,
                cmd.canalPaiement(),
                cmd.operateurMobileMoney(),
                cmd.referenceMobileMoney(),
                cmd.nomBanque(),
                cmd.referenceBancaire(),
                cmd.preuvePaiement(),
                LocalTime.now()
        );
    }


}
