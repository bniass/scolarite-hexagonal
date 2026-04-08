package com.ecole221.application.usecase.anneeacademic;

import com.ecole221.application.mapper.AnneeAcademiqueMapper;
import com.ecole221.application.port.out.repository.AnneeAcademiqueRepository;
import com.ecole221.application.query.anneeacademique.AnneeAcademiqueView;
import com.ecole221.application.port.in.anneeacademique.ConsulterAnneeAcademiqueUseCase;
import com.ecole221.domain.entity.academic.AnneeAcademique;
import com.ecole221.domain.entity.academic.AnneeAcademiqueId;
import com.ecole221.domain.exception.ScolariteNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ConsulterAnneeAcademiqueService
        implements ConsulterAnneeAcademiqueUseCase {

    private final AnneeAcademiqueRepository repository;
    private final AnneeAcademiqueMapper mapper;


    public ConsulterAnneeAcademiqueService(
            AnneeAcademiqueRepository repository, AnneeAcademiqueMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public AnneeAcademiqueView executer(String code) {

        AnneeAcademiqueId id = new AnneeAcademiqueId(
                Integer.parseInt(code)
        );

        AnneeAcademique annee = repository.findByCode(id.value())
                .orElseThrow(() -> new ScolariteNotFoundException(
                        "Année académique inexistante"
                ));

        return mapper.toView(annee);
    }
}

