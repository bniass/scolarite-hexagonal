package com.ecole221.infrastructure.web.validation;

import com.ecole221.infrastructure.web.inscription.request.CreerInscriptionRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import static com.ecole221.domain.entity.paiement.CanalPaiement.*;

@Component
public class PaiementSelonCanalValidator
        implements ConstraintValidator<ValidPaiement, PaiementRequest> {

    @Override
    public boolean isValid(
            PaiementRequest r,
            ConstraintValidatorContext context
    ) {
        System.out.println("Validator exécuté");
        System.out.println("Canal: " + r.canalPaiement());
        System.out.println("Operateur: " + r.operateurMobileMoney());

        if (r == null || r.canalPaiement() == null) {
            return true;
        }

        boolean valid = true;
        context.disableDefaultConstraintViolation();

        switch (r.canalPaiement()) {

            case MOBILE_MONEY -> {

                valid &= require(context, r.operateurMobileMoney(),
                        "operateurMobileMoney",
                        "operateurMobileMoney est obligatoire");

                valid &= requireText(context, r.referenceMobileMoney(),
                        "referenceMobileMoney",
                        "referenceMobileMoney est obligatoire");

                valid &= forbidText(context, r.nomBanque(),
                        "nomBanque",
                        "nomBanque ne doit pas être fourni");

                valid &= forbidText(context, r.referenceBancaire(),
                        "referenceBancaire",
                        "referenceBancaire ne doit pas être fourni");
            }

            case BANQUE -> {

                valid &= requireText(context, r.nomBanque(),
                        "nomBanque",
                        "nomBanque est obligatoire");

                valid &= requireText(context, r.referenceBancaire(),
                        "referenceBancaire",
                        "referenceBancaire est obligatoire");

                valid &= forbid(context, r.operateurMobileMoney(),
                        "operateurMobileMoney",
                        "operateurMobileMoney ne doit pas être fourni");

                valid &= forbidText(context, r.referenceMobileMoney(),
                        "referenceMobileMoney",
                        "referenceMobileMoney ne doit pas être fourni");
            }

            case COMPTANT -> {

                valid &= forbid(context, r.operateurMobileMoney(),
                        "operateurMobileMoney",
                        "operateurMobileMoney ne doit pas être fourni");

                valid &= forbidText(context, r.referenceMobileMoney(),
                        "referenceMobileMoney",
                        "referenceMobileMoney ne doit pas être fourni");

                valid &= forbidText(context, r.nomBanque(),
                        "nomBanque",
                        "nomBanque ne doit pas être fourni");

                valid &= forbidText(context, r.referenceBancaire(),
                        "referenceBancaire",
                        "referenceBancaire ne doit pas être fourni");

                valid &= forbidText(context, r.preuvePaiement(),
                        "preuvePaiement",
                        "preuvePaiement ne doit pas être fourni");
            }
        }

        return valid;
    }

    // ----------- helpers ------------

    private boolean require(
            ConstraintValidatorContext ctx,
            Object value,
            String field,
            String message
    ) {
        if (value == null) {
            add(ctx, field, message);
            return false;
        }
        return true;
    }

    private boolean requireText(
            ConstraintValidatorContext ctx,
            String value,
            String field,
            String message
    ) {
        if (value == null || value.isBlank()) {
            add(ctx, field, message);
            return false;
        }
        return true;
    }

    private boolean forbid(
            ConstraintValidatorContext ctx,
            Object value,
            String field,
            String message
    ) {
        if (value != null) {
            add(ctx, field, message);
            return false;
        }
        return true;
    }

    private boolean forbidText(
            ConstraintValidatorContext ctx,
            String value,
            String field,
            String message
    ) {
        if (value != null && !value.isBlank()) {
            add(ctx, field, message);
            return false;
        }
        return true;
    }

    private void add(
            ConstraintValidatorContext ctx,
            String field,
            String message
    ) {
        ctx.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(field)
                .addConstraintViolation();
    }
}