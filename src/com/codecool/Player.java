package com.codecool;

import java.util.List;

public abstract class Player {
    private Hand hand;
    private String name;

    public Card getTopCard() {
        return hand.getCards()[0];
    }
    public void addCardsToBottomOfHand(List<Card> cardList) {
        for (Card card: cardList) {
            hand.addCard(card);
        }
    }

    public Hand getHand() {
        return this.hand;
    }

    public String getName() {
        return this.name;
    }
}
