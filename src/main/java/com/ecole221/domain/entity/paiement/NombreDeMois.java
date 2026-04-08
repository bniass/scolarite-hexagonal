package com.ecole221.domain.entity.paiement;

import com.ecole221.domain.entity.academic.MoisAcademique;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record NombreDeMois(@JsonValue int value) {
    @JsonCreator
    public NombreDeMois {
        if (value <= 0) {
            throw new IllegalArgumentException(
                    "Le nombre de mois doit être strictement positif"
            );
        }
    }

    public static NombreDeMois of(int value) {
        return new NombreDeMois(value);
    }
}
