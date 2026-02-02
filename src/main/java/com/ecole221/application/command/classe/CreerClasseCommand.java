package com.ecole221.application.command.classe;

import java.math.BigDecimal;
// DTO d'entrée
public record CreerClasseCommand(
        String codeClasse,
        String nomClasse,
        String codeFiliere,
        BigDecimal fraisInscription,
        BigDecimal mensualite,
        BigDecimal autresFrais
) {}
