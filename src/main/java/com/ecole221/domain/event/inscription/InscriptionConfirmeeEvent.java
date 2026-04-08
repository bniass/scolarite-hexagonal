package com.ecole221.domain.event.inscription;

import com.ecole221.domain.entity.inscription.InscriptionId;

public class InscriptionConfirmeeEvent extends InscriptionEvent{
    public InscriptionConfirmeeEvent(InscriptionId inscriptionId){
        super(inscriptionId);
    }
}
