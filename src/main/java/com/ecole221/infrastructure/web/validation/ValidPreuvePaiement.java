package com.ecole221.infrastructure.web.validation;

import jakarta.validation.Constraint;
import org.springframework.messaging.handler.annotation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PreuvePaiementSelonCanalValidator.class)
public @interface ValidPreuvePaiement {

    String message() default "Preuve paiement invalide";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
