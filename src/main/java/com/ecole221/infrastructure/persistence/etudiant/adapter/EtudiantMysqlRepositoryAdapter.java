package com.ecole221.infrastructure.persistence.etudiant.adapter;

import com.ecole221.application.port.out.repository.EtudiantRepository;
import com.ecole221.domain.entity.student.Etudiant;
import com.ecole221.infrastructure.persistence.anneeacademique.repository.AnneeAcademiqueJpaRepository;
import com.ecole221.infrastructure.persistence.etudiant.mapper.EtudiantPersistenceMapper;
import com.ecole221.infrastructure.persistence.etudiant.repository.EtudiantJpaRepository;
import com.ecole221.infrastructure.persistence.inscription.repository.InscriptionJpaRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("mysql")
public class EtudiantMysqlRepositoryAdapter implements EtudiantRepository {
    private final EtudiantPersistenceMapper etudiantPersistenceMapper;
    private final EtudiantJpaRepository etudiantJpaRepository;
    public EtudiantMysqlRepositoryAdapter(InscriptionJpaRepository inscriptionJpaRepository, EtudiantJpaRepository etudiantJpaRepository, AnneeAcademiqueJpaRepository anneeAcademiqueJpaRepository, EtudiantPersistenceMapper etudiantPersistenceMapper) {
        this.etudiantJpaRepository = etudiantJpaRepository;
        this.etudiantPersistenceMapper = etudiantPersistenceMapper;
    }


    @Override
    public boolean existsByMatricule(String matricule) {
        return etudiantJpaRepository.existsByMatricule(matricule);
    }

    @Override
    public void save(Etudiant etudiant) {
        etudiantJpaRepository.save(etudiantPersistenceMapper.toJpa(etudiant));
    }
}
