package com.ecole221.infrastructure.persistence.filiere.entity;

import com.ecole221.infrastructure.persistence.classe.entity.ClasseJpaEntity;
import com.ecole221.infrastructure.persistence.inscription.entity.InscriptionJpaEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class FiliereJpaEntity {
    @Id
    @Column(length = 15)
    private String code;
    @Column(length = 100)
    private String nom;
    @OneToMany(mappedBy = "filiere")
    private List<ClasseJpaEntity> classeJpaEntityList;
}
