package com.ecole221.domain.entity.school;

import java.util.Objects;

public class Filiere {
    private final CodeFiliere codeFiliere;
    private final NomFiliere nomFiliere;

    public Filiere(CodeFiliere codeFiliere, NomFiliere nomFiliere) {
        this.codeFiliere = codeFiliere;
        this.nomFiliere = nomFiliere;
    }

    public CodeFiliere getCodeFiliere() {
        return codeFiliere;
    }

    public NomFiliere getNomFiliere() {
        return nomFiliere;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Filiere filiere = (Filiere) o;
        return Objects.equals(codeFiliere, filiere.codeFiliere) && Objects.equals(nomFiliere, filiere.nomFiliere);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeFiliere, nomFiliere);
    }
}
