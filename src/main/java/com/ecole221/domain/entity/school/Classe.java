package com.ecole221.domain.entity.school;

import com.ecole221.domain.shared.AggregateRoot;

import java.math.BigDecimal;

public class Classe implements AggregateRoot<CodeClasse> {
    private final CodeClasse code;
    private final NomClasse nom;
    private final Filiere filiere;

    private Montant fraisInscription;
    private Montant mensualite;
    private Montant autresFrais;

    public Classe(CodeClasse code,
                  NomClasse nom,
                  Filiere filiere,
                  Montant fraisInscription,
                  Montant mensualite,
                  Montant autresFrais) {

        this.code = code;
        this.nom = nom;
        this.filiere = filiere;
        this.fraisInscription = fraisInscription;
        this.mensualite = mensualite;
        this.autresFrais = autresFrais;
    }

    @Override
    public CodeClasse getId() {
        return code;
    }

    public NomClasse getNom() {
        return nom;
    }

    public Montant montantTotalInscription() {
        return fraisInscription
                .additionner(mensualite)
                .additionner(autresFrais);
    }

    public CodeClasse getCode() {
        return code;
    }

    public void modifierFraisInscrition(Montant nouveaufraisInscription){
        if (nouveaufraisInscription == null) {
            throw new IllegalArgumentException("Frais d'inscription invalide");
        }
        if (nouveaufraisInscription.valeur().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Les frais d'inscription doit être >= 30000");
        }
        this.fraisInscription = nouveaufraisInscription;
    }

    public void modifierMensualite(Montant nouvelleMensualite){
        if (nouvelleMensualite == null) {
            throw new IllegalArgumentException("Mensualité invalide");
        }
        if (nouvelleMensualite.valeur().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("La mensualité doit être >= 25000");
        }
        this.mensualite = nouvelleMensualite;
    }

    public void modifierAutresFrais(Montant nouvelAutresFrais){
        if (nouvelAutresFrais == null) {
            throw new IllegalArgumentException("Autres frais invalide");
        }
        if (nouvelAutresFrais.valeur().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Les autres frais doit être >= 10000");
        }
        this.autresFrais = nouvelAutresFrais;
    }

    public Filiere getFiliere() {
        return filiere;
    }

    public Montant getFraisInscription() {
        return fraisInscription;
    }

    public Montant getMensualite() {
        return mensualite;
    }

    public Montant getAutresFrais() {
        return autresFrais;
    }
}
