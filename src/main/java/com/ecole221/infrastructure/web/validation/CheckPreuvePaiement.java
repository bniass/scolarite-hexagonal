package com.ecole221.infrastructure.web.validation;

import com.ecole221.domain.entity.paiement.CanalPaiement;
import com.ecole221.domain.exception.ScolariteException;
import com.ecole221.infrastructure.web.inscription.request.CreerInscriptionRequest;
import org.springframework.web.multipart.MultipartFile;

public class CheckPreuvePaiement<T> {
    public static void validerPreuvePaiement(
            CanalPaiement canalPaiement,
            MultipartFile preuvePaiement
    ) {

        switch (canalPaiement) {

            case MOBILE_MONEY, BANQUE -> {
                if (preuvePaiement == null || preuvePaiement.isEmpty()) {
                    throw new ScolariteException(
                            "La preuve de paiement est obligatoire pour "
                                    + canalPaiement
                    );
                }
            }

            case COMPTANT -> {
                if (preuvePaiement != null && !preuvePaiement.isEmpty()) {
                    throw new ScolariteException(
                            "La preuve de paiement ne doit pas être fournie pour COMPTANT"
                    );
                }
            }
        }
    }
}
