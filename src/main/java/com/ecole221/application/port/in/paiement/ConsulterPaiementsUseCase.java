package com.ecole221.application.port.in.paiement;

import com.ecole221.application.query.paiement.ConsulterPaiementQuery;
import com.ecole221.application.query.paiement.PaiementView;

import java.util.List;

public interface ConsulterPaiementsUseCase {
    List<PaiementView> execute(ConsulterPaiementQuery consulterPaiementQuery);
}
