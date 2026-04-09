package com.ecole221.domain.service.paiement;

import com.ecole221.application.command.paiement.DeclarerPaiementCommand;
import com.ecole221.application.factory.paiement.PaiementFactory;
import com.ecole221.domain.entity.paiement.Paiement;
import com.ecole221.domain.entity.paiement.StatutPaiement;
import com.ecole221.domain.entity.paiement.TypePaiement;
import com.ecole221.domain.entity.school.Classe;
import com.ecole221.domain.entity.school.Montant;
import com.ecole221.domain.exception.ScolariteException;
import com.ecole221.domain.policy.paiement.PaiementPolicy;
import com.ecole221.domain.result.paiement.PaiementProcessingResult;
import com.ecole221.infrastructure.persistence.paiement.adapter.PaiementPersistenceContext;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class PaiementMensualiteProcessor {

    public PaiementProcessingResult process(
            List<Paiement> paiements,
            Classe classe,
            DeclarerPaiementCommand cmd,
            String nomFichier
    ) {
        BigDecimal mensualite = classe.getMensualite().valeur();
        BigDecimal montantVerse = BigDecimal.valueOf(cmd.montantPaye());
        BigDecimal totalDu = PaiementPolicy.calculerTotalRestantDu(paiements);

        if (montantVerse.compareTo(totalDu) > 0) {
            throw new ScolariteException(
                    "Le montant ne peut pas dépasser la somme totale du (" + totalDu + ")"
            );
        }
        BigDecimal montantRestant =
                BigDecimal.valueOf(cmd.montantPaye());
        DeclarerPaiementCommand paiementCmd = cmd.withPreuvePaiement(nomFichier);

        List<Paiement> paiementsAMettreAJour = new ArrayList<>();
        List<PaiementPersistenceContext> contexts = new ArrayList<>();

        for (Paiement paiementInitial : paiements) {
            if (montantRestant.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }
            StatutPaiement statutInitial =
                    paiementInitial.getStatut();
            // Cas : Compléter mois en avance
            if (statutInitial == StatutPaiement.AVANCE) {
                BigDecimal restantMois =
                        mensualite.subtract(paiementInitial.getMontant().valeur());
                if (montantRestant.compareTo(restantMois) >= 0) {
                    // compléter le mois intégralement
                    ajouterPaiement(
                            paiementInitial,
                            cmd,
                            mensualite,
                            new Montant(paiementInitial.getMontant().valeur().add(restantMois)).valeur(),
                            StatutPaiement.PAYE,
                            paiementInitial.getMontant().valeur().add(restantMois),
                            paiementsAMettreAJour,
                            contexts
                    );
                    montantRestant =
                            montantRestant.subtract(restantMois);
                }
                else
                {
                    // avance sur le montant restant à completer du mois.
                    ajouterPaiement(
                            paiementInitial,
                            cmd,
                            mensualite,
                            new Montant(paiementInitial.getMontant().valeur().add(montantRestant)).valeur(),
                            StatutPaiement.AVANCE,
                            paiementInitial.getMontant().valeur().add(montantRestant),
                            paiementsAMettreAJour,
                            contexts
                    );
                    montantRestant =
                            montantRestant.subtract(restantMois);
                }
            } else {
                // Paiement mois complet (1 ou plusieurs)
                if (montantRestant.compareTo(mensualite) >= 0) {
                    //paiement complet d'un mois
                    ajouterPaiement(
                            paiementInitial,
                            cmd,
                            mensualite,
                            new Montant(paiementInitial.getMontant().valeur().add(mensualite)).valeur(),
                            StatutPaiement.PAYE,
                            paiementInitial.getMontant().valeur().add(mensualite),
                            paiementsAMettreAJour,
                            contexts
                    );
                    montantRestant =
                            montantRestant.subtract(mensualite);
                }
                else if (montantRestant.compareTo(montantRestant) >= 0) {
                    // Avance sur un mois
                    ajouterPaiement(
                            paiementInitial,
                            cmd,
                            mensualite,
                            new Montant(paiementInitial.getMontant().valeur().add(montantRestant)).valeur(),
                            StatutPaiement.AVANCE,
                            paiementInitial.getMontant().valeur().add(montantRestant),
                            paiementsAMettreAJour,
                            contexts
                    );
                    montantRestant =
                            montantRestant.subtract(montantRestant);

                }
            }
        }

        return new PaiementProcessingResult(
                paiementsAMettreAJour,
                contexts,
                paiementsAMettreAJour.stream().map(p->p.getId().value()).toList()
        );
    }

    private void ajouterPaiement(
            Paiement paiementInitial,
            DeclarerPaiementCommand cmd,
            BigDecimal mensualite,
            BigDecimal montant,
            StatutPaiement statut,
            BigDecimal montantContext,
            List<Paiement> paiements,
            List<PaiementPersistenceContext> contexts
    ) {

        Paiement paiement = Paiement.reconstituer(
                paiementInitial.getId(),
                paiementInitial.getInscriptionId(),
                cmd.datePaiement(),
                new Montant(montant),
                new Montant(mensualite),
                TypePaiement.MENSUALITE,
                cmd.canalPaiement(),
                paiementInitial.getMoisAcademique(),
                statut
        );

        paiements.add(paiement);

        contexts.add(
                PaiementFactory.creerPaiementPersistenceContexte(
                        cmd,
                        paiement.getId().value(),
                        montantContext.intValue()
                )
        );
    }
}
