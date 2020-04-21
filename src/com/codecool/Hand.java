package com.codecool;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class Hand {
    private List<Card> cardsOnHand;
    private Iterator<Card> cardIterator;

    public Hand() {
        this.cardsOnHand = new ArrayList<>();
        this.cardIterator = new CardIterator();
    }

    public void addCard(Card card) {
        this.cardsOnHand.add(card);
    }

    private class CardIterator implements Iterator<Card> {
        int index;

        @Override
        public boolean hasNext() {
            return index < cardsOnHand.size();
        }

        @Override
        public Card next() {
            if (this.hasNext()) {
                return cardsOnHand.get(index++);
            } else {
                return null;
            }
        }
    }

    public Iterator<Card> getIterator() {
        return this.cardIterator;
    }

    public List<Card> getCardsOnHand() {
        return cardsOnHand;
    }
}
