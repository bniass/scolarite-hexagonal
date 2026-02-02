package com.ecole221.domain.event.inscription;

import com.ecole221.domain.entity.inscription.InscriptionId;

public class InscriptionCreee extends InscriptionEvent{
    public InscriptionCreee(InscriptionId inscriptionId){
        super(inscriptionId);

    }
}
