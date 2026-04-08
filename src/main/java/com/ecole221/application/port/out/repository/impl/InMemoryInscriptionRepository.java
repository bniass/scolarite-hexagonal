package com.ecole221.application.port.out.repository.impl;

import com.ecole221.application.port.out.repository.InscriptionRepository;
import com.ecole221.domain.entity.academic.AnneeAcademique;
import com.ecole221.domain.entity.academic.AnneeAcademiqueId;
import com.ecole221.domain.entity.inscription.Inscription;
import com.ecole221.domain.entity.inscription.InscriptionId;
import com.ecole221.domain.entity.student.Matricule;
import com.ecole221.infrastructure.persistence.inscription.entity.InscriptionJpaEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile({"inmemory", "test"})
public class InMemoryInscriptionRepository implements InscriptionRepository {

    private final List<Inscription> data = new ArrayList<>();

    @Override
    public void save(Inscription inscription) {
        data.add(inscription);
    }

    @Override
    public Optional<Inscription> findByMatriculeAndAnnee(String matricule, String annee) {
        InscriptionId inscriptionId = new InscriptionId(
                new Matricule(matricule),
                new AnneeAcademiqueId(
                        Integer.parseInt(annee)
                )
        );

        return data.stream()
                .filter(i -> i.getId().equals(inscriptionId))
                .findFirst();
    }


    @Override
    public boolean existsByMatriculeAndAnnee(String matricule, String anneeAcademic) {
        return false;
    }

    @Override
    public long count() {
        return data.size();
    }

    public List<Inscription> all() {
        return data;
    }

    @Override
    public void delete(Inscription inscription) {
        data.remove(
                findByMatriculeAndAnnee(
                        inscription.getId().getMatricule().value(),
                        inscription.getId().getAnneeAcademiqueId().value()
                )
        );
    }

}

