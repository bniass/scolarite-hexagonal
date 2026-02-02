package com.ecole221.domain.event.shared;

import java.util.ArrayList;
import java.util.List;

public abstract class AggregateRoot {

    private final List<DomainEvent> domainEvents = new ArrayList<>();

    public void addEvent(DomainEvent event) {
        domainEvents.add(event);
    }
    //extraire les événements métier produits par l’Aggregate et les consommer une seule fois
    public List<DomainEvent> pullDomainEvents() {
        List<DomainEvent> events = List.copyOf(domainEvents);
        domainEvents.clear();
        return events;
    }
}
