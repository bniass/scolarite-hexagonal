package com.ecole221.domain.entity.academic;

import com.ecole221.domain.event.shared.AggregateRoot;
import com.ecole221.domain.exception.ScolariteException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AnneeAcademique extends AggregateRoot {

    private static final int DUREE_ANNEE_SCOLAIRE_MOIS = 9;

    private final AnneeAcademiqueId id;

    private LocalDate dateDebut;
    private LocalDate dateFin;
    private LocalDate dateOuvertureInscription;
    private LocalDate dateFinInscription;
    private LocalDate datePublication;
    private List<MoisAcademique> moisAcademiques = new ArrayList<>();

    private EtatAnnee etat;
    /* =======================
       CONSTRUCTEUR
       ======================= */

    public AnneeAcademique(AnneeAcademiqueId id) {
        this.id = Objects.requireNonNull(id, "id obligatoire");
        etat = new AnneeBrouillon();
    }

    public static AnneeAcademique reconstituer(String code, DatesAnnee datesAnnee, LocalDate datePublication, EtatAnnee etat, List<MoisAcademique> mois) {
        String[] annees = code.split("-");
        AnneeAcademiqueId id = new AnneeAcademiqueId(Integer.parseInt(annees[0]), Integer.parseInt(annees[1]));
        AnneeAcademique annee = new AnneeAcademique(id);
        annee.dateDebut = datesAnnee.getDateDebut();
        annee.dateFin = datesAnnee.getDateFin();
        annee.dateOuvertureInscription = datesAnnee.getDateOuvertureInscription();
        annee.dateFinInscription = datesAnnee.getDateFinInscription();
        annee.datePublication = datePublication;
        annee.etat = etat;
        annee.moisAcademiques = mois;
        return annee;
    }

    /* =======================
       COMPORTEMENTS METIER
       ======================= */

    public void creer(DatesAnnee datesAnnee) {
        etat.creer(this, datesAnnee);
    }

    public void modifier(DatesAnnee datesAnnee) {
        etat.modifier(this, datesAnnee);
    }

    public void publier() {
        etat.publier(this);
        this.datePublication = LocalDate.now();
    }

    public void ouvrirInscriptions() {
        etat.ouvrirInscriptions(this);
    }

    public void cloturer() {
        etat.cloturer(this);
    }

    public void suspendreInscriptions() {
        etat.suspendreInscriptions(this);
    }

    public void fermerInscriptions(){
        etat.fermerInscriptions(this);
    }

    /* =======================
       INVARIANTS METIER
       ======================= */

    public void initialiserDates(DatesAnnee datesAnnee)
    {
        if (!datesAnnee.getDateDebut().isBefore(datesAnnee.getDateFin())) {
            throw new ScolariteException(
                    "La date de début de l'année doit être antérieure à la date de fin de la l'année académique"
            );
        }

        if (!datesAnnee.getDateOuvertureInscription().isBefore(datesAnnee.getDateFinInscription())) {
            throw new ScolariteException("Période d’inscription invalide");
        }

        if (datesAnnee.getDateOuvertureInscription().isAfter(datesAnnee.getDateDebut())) {
            throw new ScolariteException(
                    "L’ouverture des inscriptions doit être antérieure ou égale au début de l’année"
            );
        }

        if (!datesAnnee.getDateFinInscription().isAfter(datesAnnee.getDateDebut())) {
            throw new ScolariteException(
                    "La fin des inscriptions doit être postérieure au début de l’année"
            );
        }

        if (datesAnnee.getDateFinInscription().isAfter(datesAnnee.getDateFin())) {
            throw new ScolariteException(
                    "La date de fin des inscriptions doit être antérieure ou égale à la fin de l’année"
            );
        }

        this.dateDebut = datesAnnee.getDateDebut();
        this.dateFin = datesAnnee.getDateFin();
        this.dateOuvertureInscription = datesAnnee.getDateOuvertureInscription();
        this.dateFinInscription = datesAnnee.getDateFinInscription();
    }

    public void verifierDureeAnnee() {
        long mois = ChronoUnit.MONTHS.between(
                dateDebut.withDayOfMonth(1),
                dateFin.withDayOfMonth(1)
        );

        if (dateDebut.plusMonths(mois).isBefore(dateFin)) {
            mois++;
        }

        if (mois != DUREE_ANNEE_SCOLAIRE_MOIS) {
            throw new ScolariteException(
                    "Une année scolaire doit couvrir exactement "
                            + DUREE_ANNEE_SCOLAIRE_MOIS + " mois"
            );
        }
    }

    public List<MoisAcademique> genererMoisAcademiques() {
        List<MoisAcademique> mois = new ArrayList<>();
        LocalDate courant = dateDebut.withDayOfMonth(1);

        for (int i = 0; i < DUREE_ANNEE_SCOLAIRE_MOIS; i++) {
            mois.add(new MoisAcademique(courant.getMonthValue(), courant.getYear()));
            courant = courant.plusMonths(1);
        }
        return mois;
    }


    /* =======================
       GETTERS
       ======================= */

    public AnneeAcademiqueId getId() {
        return id;
    }

    public List<MoisAcademique> moisAcademiques() {
        return List.copyOf(moisAcademiques);
    }

    public LocalDate getDatePublication() {
        return datePublication;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public LocalDate getDateOuvertureInscription() {
        return dateOuvertureInscription;
    }

    public LocalDate getDateFinInscription() {
        return dateFinInscription;
    }

    public List<MoisAcademique> getMoisAcademiques() {
        return moisAcademiques;
    }

    protected void changerEtat(EtatAnnee etatState) {
        this.etat = Objects.requireNonNull(etatState);
    }

    public EtatAnnee getEtat() {
        return this.etat;
    }

    void recalculerMois(List<MoisAcademique> moisAcademiques) {
        this.moisAcademiques.clear();
        this.moisAcademiques.addAll(moisAcademiques);
    }

    public void verifierInscriptionsOuvertes() {
        etat.verifierInscriptionsOuvertes();
    }
}
