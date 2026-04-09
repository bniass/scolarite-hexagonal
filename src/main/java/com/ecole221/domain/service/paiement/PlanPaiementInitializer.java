package com.ecole221.domain.service.paiement;


import com.ecole221.application.command.paiement.DeclarerPaiementCommand;
import com.ecole221.application.factory.paiement.PaiementFactory;
import com.ecole221.domain.entity.academic.MoisAcademique;
import com.ecole221.domain.entity.inscription.InscriptionCreationSnapshot;
import com.ecole221.domain.entity.paiement.CanalPaiement;
import com.ecole221.domain.entity.paiement.Paiement;
import com.ecole221.domain.entity.paiement.PaiementId;
import com.ecole221.domain.entity.paiement.StatutPaiement;
import com.ecole221.domain.entity.school.Classe;
import com.ecole221.domain.entity.school.Montant;
import com.ecole221.domain.exception.ScolariteException;
import com.ecole221.domain.result.paiement.PaiementProcessingResult;
import com.ecole221.infrastructure.persistence.paiement.adapter.PaiementPersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class PlanPaiementInitializer {

    public PaiementProcessingResult initialiser(
            InscriptionCreationSnapshot snapshot,
            DeclarerPaiementCommand cmd,
            Classe classe,
            List<MoisAcademique> moisTries
    ) {
        try {
            if (moisTries == null || moisTries.isEmpty()) {
                throw new ScolariteException("Aucun mois académique disponible");
            }

            double totalDu = classe.getFraisInscription().valeur().doubleValue()
                    + classe.getAutresFrais().valeur().doubleValue()
                    + classe.getMensualite().valeur().doubleValue() * 9;

            // mont versé ne doit pas dépassé total à payer dans l'année
            if(totalDu < cmd.montantPaye()){
                throw new ScolariteException(
                        "Montant incorrect. Max Attendu : " + totalDu
                );
            }

            //le montant à verser doit couvrir au moins les frais d'inscription et les autres frais
            double fraisRequis = classe.getFraisInscription().valeur().doubleValue()
                    + classe.getAutresFrais().valeur().doubleValue();
            if(cmd.montantPaye() < fraisRequis){
                throw new ScolariteException(
                        "Frais inscription et autres frais doivent être couvert ("+fraisRequis+") lors de l'inscription."
                );
            }

            double nbMois = (cmd.montantPaye() -
                    (classe.getFraisInscription().valeur().doubleValue() +
                            classe.getAutresFrais().valeur().doubleValue())) / classe.getMensualite().valeur().intValue();
            int nombreDeMois = (int)nbMois;


            if(nbMois - (int)nbMois > 0.0){
                nombreDeMois++;
            }

            List<MoisAcademique> moisPayes = determinerMoisPayes(moisTries, nombreDeMois);

            List<MoisAcademique> moisImpayes = moisTries.stream()
                    .filter(m -> !moisPayes.contains(m))
                    .toList();

            List<Paiement> paiements = new ArrayList<>();
            List<PaiementPersistenceContext> contexts = new ArrayList<>();

            List<UUID> ids = new ArrayList<>();

        /* ============================================================
           1 - FRAIS INSCRIPTION
           ============================================================ */
            Paiement fraisInscription = PaiementFactory.creerPaiementFraisInscription(cmd);
            fraisInscription.modifierMontantPrevu(classe.getFraisInscription());
            fraisInscription.attribuerStatut(StatutPaiement.PAYE);

            paiements.add(fraisInscription);
            ids.add(fraisInscription.getId().value());

            PaiementPersistenceContext contextFI =
                    PaiementFactory.creerPaiementPersistenceContexte(
                            cmd,
                            fraisInscription.getId().value(),
                            classe.getFraisInscription().valeur().intValue()
                    );
            contexts.add(contextFI);

        /* ============================================================
           2 - FRAIS DIVERS
           ============================================================ */
            Paiement fraisDivers = PaiementFactory.creerPaiementFraisDivers(cmd);
            fraisDivers.modifierMontantPrevu(classe.getAutresFrais());
            fraisDivers.attribuerStatut(StatutPaiement.PAYE);

            paiements.add(fraisDivers);
            ids.add(fraisDivers.getId().value());

            PaiementPersistenceContext contextFD =
                    PaiementFactory.creerPaiementPersistenceContexte(
                            cmd,
                            fraisDivers.getId().value(),
                            classe.getAutresFrais().valeur().intValue()
                    );
            contexts.add(contextFD);

        /* ============================================================
           3 - MOIS PAYÉS
           ============================================================ */
            double lesMensualitesAdispatcher = cmd.montantPaye() - fraisRequis;
            for (MoisAcademique mois : moisPayes) {
                Paiement paiementMensualite =
                        PaiementFactory.creerPaiementMensualite(cmd, mois);

                ids.add(paiementMensualite.getId().value());

                paiementMensualite.modifierMontantPrevu(classe.getMensualite());
                paiementMensualite.attribuerStatut(StatutPaiement.PAYE);
                paiements.add(paiementMensualite);

                PaiementPersistenceContext contextP =
                        PaiementFactory.creerPaiementPersistenceContexte(
                                cmd,
                                paiementMensualite.getId().value(),
                                classe.getMensualite().valeur().intValue()
                        );

                contexts.add(contextP);
                lesMensualitesAdispatcher -= classe.getMensualite().valeur().doubleValue();
            }

        /* ============================================================
           4 - CAS PAIEMENT PARTIEL (avance d’un demi-mois)
           ============================================================ */
            if (lesMensualitesAdispatcher < 0.0) {

                int indexPaiementAModifier;

                if (moisPayes.size() == 1) {
                    indexPaiementAModifier = contexts.size() - 1;
                } else {
                    indexPaiementAModifier = contexts.size() - 2;
                }

                Paiement paiement = paiements.get(indexPaiementAModifier);

                paiement.modifierMontantPrevu(classe.getMensualite());
                paiement.attribuerStatut(StatutPaiement.AVANCE);

                PaiementPersistenceContext nouveauContext =
                        PaiementFactory.creerPaiementPersistenceContexte(
                                cmd,
                                paiement.getId().value(),
                                classe.getMensualite().valeur().intValue() + (int)lesMensualitesAdispatcher
                        );

                contexts.set(indexPaiementAModifier, nouveauContext);
            }


        /* ============================================================
           5 - MOIS IMPAYÉS
           ============================================================ */
            for (MoisAcademique mois : moisImpayes) {
                Paiement paiementInitial =
                        PaiementFactory.creerPaiementMensualiteInitial(
                                snapshot.inscriptionId(),
                                mois,
                                cmd.canalPaiement()
                        );

                paiementInitial.modifierMontantPrevu(classe.getMensualite());
                paiementInitial.attribuerStatut(StatutPaiement.IMPAYE);

                paiements.add(paiementInitial);

                DeclarerPaiementCommand futurPaiement = createFuturPaiement(
                        cmd.matricule(),
                        cmd.anneeAcademique()
                );

                PaiementPersistenceContext contextImp =
                        PaiementFactory.creerPaiementPersistenceContexte(
                                futurPaiement,
                                paiementInitial.getId().value(),
                                classe.getMensualite().valeur().intValue()
                        );

                contexts.add(contextImp);
            }

            return new PaiementProcessingResult(
                    paiements,
                    contexts,
                    ids
            );
        } catch (ScolariteException e) {
            throw e;
        }
    }

    private List<MoisAcademique> determinerMoisPayes(
            List<MoisAcademique> tousLesMois,
            int nbPayes
    ) {
        MoisAcademique dernierMois = tousLesMois.get(tousLesMois.size() - 1);

        List<MoisAcademique> moisPayes = new ArrayList<>();

        if (nbPayes == 1) {
            moisPayes.add(dernierMois);
            return moisPayes;
        }

        moisPayes.addAll(tousLesMois.subList(0, nbPayes - 1));
        moisPayes.add(dernierMois);

        return moisPayes;
    }

    private Montant calculerMontantAttenduInscription(
            Classe classe,
            Integer nombreDeMois
    ) {
        return classe.getFraisInscription()
                .additionner(classe.getAutresFrais())
                .additionner(
                        new Montant(
                                classe.getMensualite()
                                        .valeur()
                                        .multiply(BigDecimal.valueOf(nombreDeMois))
                        )
                );
    }

    private MontantValidation calculerMontantsInitials(
            Integer nombreDeMois,
            Classe classe
    ) {
        Montant montantAttenduMax = calculerMontantAttenduInscription(
                classe,
                nombreDeMois
        );

        BigDecimal montantAttenduMin = montantAttenduMax.valeur().subtract(
                classe.getMensualite().valeur()
                        .divide(BigDecimal.valueOf(2))
        );

        return new MontantValidation(
                montantAttenduMax.valeur(),
                montantAttenduMin
        );
    }

    private void validerMontantInitial(
            double leMontantPaye,
            Classe classe,
            Integer nombreDeMois
    ) {
        MontantValidation montants = calculerMontantsInitials(nombreDeMois, classe);

        BigDecimal montantAttenduMax = montants.montantAttenduMax();
        BigDecimal montantAttenduMin = montants.montantAttenduMin();
        BigDecimal montantPaye = BigDecimal.valueOf(leMontantPaye);

        if (montantPaye.compareTo(montantAttenduMax) != 0
                && montantPaye.compareTo(montantAttenduMin) != 0) {
            throw new ScolariteException(
                    "Montant incorrect. Attendu : [Paiement complet :" +
                            montantAttenduMax.intValue() +
                            ", Paiement partiel :" +
                            montantAttenduMin.intValue() +
                            "]"
            );
        }
    }

    private DeclarerPaiementCommand createFuturPaiement(
            String matricule,
            String annee
    ) {
        return new DeclarerPaiementCommand(
                matricule,
                annee,
                "",
                CanalPaiement.NOT_YET_PAIED,
                "",
                "",
                "",
                "",
                0.0,
                null,
                null,
                ""
        );
    }
}
