package com.ecole221.infrastructure.persistence.anneeacademique.mapper;

    import com.ecole221.domain.entity.academic.*;
    import com.ecole221.infrastructure.persistence.anneeacademique.entity.AnneeAcademiqueJpaEntity;
    import com.ecole221.infrastructure.persistence.anneeacademique.entity.AnneeMoisJpaEntity;
    import org.springframework.stereotype.Component;

    import java.util.List;

    @Component
    public class AnneeAcademiquePersistenceMapper {

        /* =======================
           DOMAIN → JPA
           ======================= */

        public AnneeAcademiqueJpaEntity toJpa(AnneeAcademique domain) {
            AnneeAcademiqueJpaEntity entity = new AnneeAcademiqueJpaEntity();
            entity.setCode(domain.getId().value());
            entity.setDateDebut(domain.getDateDebut());
            entity.setDateFin(domain.getDateFin());
            entity.setDateDebutInscriptions(domain.getDateOuvertureInscription());
            entity.setDateFinInscriptions(domain.getDateFinInscription());
            entity.setDatePublication(domain.getDatePublication());
            entity.setStatut(
                    EtatAnneeFactory.toStatut(domain.getEtat())
            );

            /* =======================
               MOIS ACADEMIQUES
               ======================= */
            if(domain.getEtat() instanceof AnneeBrouillon){
                entity.getMoisAcademiques().clear();

                for (MoisAcademique m : domain.getMoisAcademiques()) {
                    entity.getMoisAcademiques().add(
                            new AnneeMoisJpaEntity(
                                    domain.getId().value(),
                                    m.mois(),
                                    m.annee()
                            )
                    );
                }
            }

            return entity;
        }

            /* =======================
           DOMAIN → JPA (UPDATE)
           ======================= */

        public void updateJpa(
                AnneeAcademique domain,
                AnneeAcademiqueJpaEntity entity
        ) {
            entity.setDateDebut(domain.getDateDebut());
            entity.setDateFin(domain.getDateFin());
            entity.setDateDebutInscriptions(domain.getDateOuvertureInscription());
            entity.setDateFinInscriptions(domain.getDateFinInscription());
            entity.setDatePublication(domain.getDatePublication());
            entity.setStatut(
                    EtatAnneeFactory.toStatut(domain.getEtat())
            );
        }


        /* =======================
           JPA → DOMAIN
           ======================= */

        public AnneeAcademique toDomain(AnneeAcademiqueJpaEntity entity) {

            List<MoisAcademique> mois = entity.getMoisAcademiques()
                    .stream()
                    .map(m -> new MoisAcademique(
                            m.getMois(),
                            m.getAnnee()
                    ))
                    .toList();

            return AnneeAcademique.reconstituer(
                    entity.getCode(),
                    new DatesAnnee(
                            entity.getDateDebut(),
                            entity.getDateFin(),
                            entity.getDateDebutInscriptions(),
                            entity.getDateFinInscriptions()
                    ),
                    entity.getDatePublication(),
                    EtatAnneeFactory.fromStatut(
                            Statut.valueOf(entity.getStatut().name())
                    ),
                    mois
            );
        }
    }
