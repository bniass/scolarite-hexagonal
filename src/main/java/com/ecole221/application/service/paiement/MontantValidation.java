package com.ecole221.application.service.paiement;

import java.math.BigDecimal;

public record MontantValidation(
        BigDecimal montantAttenduMax,
        BigDecimal montantAttenduMin
) {}
