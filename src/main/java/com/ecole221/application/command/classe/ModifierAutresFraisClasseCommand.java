package com.ecole221.application.command.classe;

import java.math.BigDecimal;

public record ModifierAutresFraisClasseCommand(
        String codeClasse,
        BigDecimal nouveauxAutresFrais
) {}
