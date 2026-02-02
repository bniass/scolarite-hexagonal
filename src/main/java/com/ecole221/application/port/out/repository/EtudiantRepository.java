package com.ecole221.application.port.out.repository;

import com.ecole221.domain.entity.student.Etudiant;

public interface EtudiantRepository {
    public boolean existsByMatricule(String matricule);
    public void save(Etudiant etudiant);
}
