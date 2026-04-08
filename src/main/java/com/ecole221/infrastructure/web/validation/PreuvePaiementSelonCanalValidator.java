package com.ecole221.infrastructure.web.validation;

import com.ecole221.infrastructure.web.inscription.request.CreerInscriptionRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class PreuvePaiementSelonCanalValidator
        implements ConstraintValidator<ValidPreuvePaiement, Object[]> {

    @Override
    public boolean isValid(Object[] args, ConstraintValidatorContext context) {

        if (args == null || args.length < 2) return true;

        CreerInscriptionRequest request =
                (CreerInscriptionRequest) args[0];

        MultipartFile preuve =
                (MultipartFile) args[1];

        if (request == null || request.canalPaiement() == null)
            return true;

        boolean valid = true;
        context.disableDefaultConstraintViolation();

        switch (request.canalPaiement()) {

            case MOBILE_MONEY, BANQUE -> {
                if (preuve == null || preuve.isEmpty()) {
                    context.buildConstraintViolationWithTemplate(
                                    "La preuve de paiement est obligatoire pour "
                                            + request.canalPaiement())
                            .addConstraintViolation();
                    valid = false;
                }
            }

            case COMPTANT -> {
                if (preuve != null && !preuve.isEmpty()) {
                    context.buildConstraintViolationWithTemplate(
                                    "La preuve ne doit pas être fournie pour COMPTANT")
                            .addConstraintViolation();
                    valid = false;
                }
            }
        }

        return valid;
    }
}
