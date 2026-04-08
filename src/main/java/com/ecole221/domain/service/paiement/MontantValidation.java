package com.ecole221.domain.service.paiement;

import java.math.BigDecimal;

public record MontantValidation(
        BigDecimal montantAttenduMax,
        BigDecimal montantAttenduMin
) {
}
