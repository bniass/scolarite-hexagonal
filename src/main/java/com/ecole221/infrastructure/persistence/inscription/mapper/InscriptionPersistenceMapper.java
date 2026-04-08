package com.ecole221.infrastructure.persistence.inscription.mapper;

import com.ecole221.domain.entity.academic.AnneeAcademique;
import com.ecole221.domain.entity.academic.AnneeAcademiqueId;
import com.ecole221.domain.entity.inscription.Inscription;
import com.ecole221.domain.entity.inscription.InscriptionCreationSnapshot;
import com.ecole221.domain.entity.inscription.InscriptionId;
import com.ecole221.domain.entity.school.CodeClasse;
import com.ecole221.domain.entity.student.Matricule;
import com.ecole221.infrastructure.persistence.anneeacademique.entity.AnneeAcademiqueJpaEntity;
import com.ecole221.infrastructure.persistence.classe.entity.ClasseJpaEntity;
import com.ecole221.infrastructure.persistence.classe.mapper.ClassePersistenceMapper;
import com.ecole221.infrastructure.persistence.etudiant.entity.EtudiantJpaEntity;
import com.ecole221.infrastructure.persistence.inscription.entity.InscriptionJpaEntity;
import com.ecole221.infrastructure.persistence.inscription.entity.InscriptionJpaId;
import org.springframework.stereotype.Component;

@Component
public class InscriptionPersistenceMapper {

    private final ClassePersistenceMapper classePersistenceMapper;

    public InscriptionPersistenceMapper(
            ClassePersistenceMapper classePersistenceMapper) {
        this.classePersistenceMapper = classePersistenceMapper;
    }

    public InscriptionJpaEntity toJpa(Inscription domain) {

        InscriptionJpaEntity entity = new InscriptionJpaEntity();

        // 1️⃣ Clé primaire composite (OBLIGATOIRE)
        entity.setId(
                new InscriptionJpaId(
                        domain.getId().getMatricule().value(),
                        domain.getId().getAnneeAcademiqueId().value()
                )
        );

        // 2️⃣ Champs simples
        entity.setDateInscription(domain.getDateInscription());

        // 3️⃣ Classe (agrégat référencé)

        ClasseJpaEntity classeJpaEntity = new ClasseJpaEntity();
        classeJpaEntity.setCode(domain.getCodeClasse().value());

        entity.setClasse(classeJpaEntity);

        // 4️⃣ Étudiant (référence par identité)
        EtudiantJpaEntity etudiantRef = new EtudiantJpaEntity();
        etudiantRef.setMatricule(
                domain.getId().getMatricule().value()
        );
        entity.setEtudiant(etudiantRef);

        // 5️⃣ Année académique (référence par identité)
        AnneeAcademiqueJpaEntity anneeRef = new AnneeAcademiqueJpaEntity();
        anneeRef.setCode(
                domain.getId().getAnneeAcademiqueId().value()
        );
        entity.setAnneeAcademique(anneeRef);

        return entity;
    }

    public Inscription toDomain(InscriptionJpaEntity entity) {
        return Inscription.creerNouvelle(
                new InscriptionCreationSnapshot(
                        new InscriptionId(
                                new Matricule(
                                        entity.getEtudiant().getMatricule()
                                ),
                                new AnneeAcademiqueId(
                                        Integer.parseInt(entity.getAnneeAcademique().getCode().split("-")[0])
                                )
                        ),
                        new CodeClasse(entity.getClasse().getCode()),
                        null,
                        null,
                        null

                )
        );

    }
}

