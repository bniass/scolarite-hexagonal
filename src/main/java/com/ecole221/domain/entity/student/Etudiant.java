package com.ecole221.domain.entity.student;

import java.time.LocalDate;

public class Etudiant {
    private final Matricule matricule;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;

    public Etudiant(Matricule matricule, String nom, String prenom, LocalDate dateNaissance) {
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
    }

    public static Etudiant creer(String matricule, String nom, String prenom, LocalDate dateNaissance) {
        return new Etudiant(new Matricule(matricule), nom, prenom, dateNaissance);
    }

    public Matricule getMatricule() {
        return matricule;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void changerNom(String nouveauNom) {
        this.nom = nouveauNom;
    }

    public void corrigerPrenom(String prenom){
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void changerDateNaissance(LocalDate detaNaissance){
        this.dateNaissance = dateNaissance;
    }
}
