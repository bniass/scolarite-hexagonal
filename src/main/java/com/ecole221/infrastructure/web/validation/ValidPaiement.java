package com.ecole221.infrastructure.web.validation;

import jakarta.validation.Constraint;
import org.springframework.messaging.handler.annotation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PaiementSelonCanalValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPaiement {
    String message() default "Informations de paiement invalides selon le canal";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
