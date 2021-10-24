package com.development.task3.builder;

import com.development.task3.entity.AbstractPostalCard;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractCardBuilder {
    protected Set<AbstractPostalCard> postalCards;

    protected AbstractCardBuilder() {
        postalCards = new HashSet<>();
    }

    protected AbstractCardBuilder(Set<AbstractPostalCard> postalCards) {
        this.postalCards = postalCards;
    }

    public Set<AbstractPostalCard> getPostalCards() {
        return postalCards;
    }

    public abstract void buildSetCards(String filename);
}
