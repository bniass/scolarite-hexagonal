package com.ecole221.application.port.out.repository.impl;

import com.ecole221.application.port.out.repository.InscriptionRepository;
import com.ecole221.domain.entity.inscription.Inscription;
import com.ecole221.infrastructure.persistence.inscription.entity.InscriptionJpaEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        /*return data.stream()
                .filter(i -> {
                        AnneeAcademique anneeAcademique = AnneeAcademique.aPartirDe(annee.split("-")[0]);
                        return i.getId().getMatricule().value().equals(matricule)
                        && i.getId().getAnneeAcademique().debut() == anneeAcademique.debut() &&
                                i.getId().getAnneeAcademique().fin() == anneeAcademique.fin();
                        }
                )
                .findFirst();*/
        return null;
    }

    @Override
    public long count() {
        return data.size();
    }

    public List<Inscription> all() {
        return data;
    }


}

