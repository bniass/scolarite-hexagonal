package com.ecole221.infrastructure.web.anneeacdemique.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record CreerAnneeAcademiqueRequest(

        @NotBlank(message = "Le code de l'année académique est obligatoire")

        @Pattern(
                regexp = "^[0-9]{4}-[0-9]{4}$",
                message = "Le format de l'année académique doit être AAAA-AAAA"
        )String code,
        LocalDate dateDebut,
        LocalDate dateFin,
        LocalDate dateDebutInscriptions,
        LocalDate dateFinInscriptions
) {}
