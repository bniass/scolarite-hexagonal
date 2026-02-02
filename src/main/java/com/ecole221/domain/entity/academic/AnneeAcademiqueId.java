package com.ecole221.domain.entity.academic;

import com.ecole221.domain.exception.ScolariteException;
import com.ecole221.domain.shared.EntityId;

public final class AnneeAcademiqueId extends EntityId<String> {
    private final int debut;
    private final int fin;

    public AnneeAcademiqueId(int debut, int fin) {
        super(validerEtConstruire(debut, fin));
        this.debut = debut;
        this.fin = fin;
    }

    private static String validerEtConstruire(int debut, int fin) {
        if (fin != debut + 1) {
            throw new ScolariteException(
                    "Une année académique doit couvrir deux années consécutives"
            );
        }
        return debut + "-" + fin;
    }

    public int debut() {
        return debut;
    }

    public int fin() {
        return fin;
    }
}
