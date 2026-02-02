package com.ecole221.infrastructure.web.classe.request;

import java.math.BigDecimal;

public record ModifierFraisInscriptionRequest(
        BigDecimal nouveauMontant
) {}
