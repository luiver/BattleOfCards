package com.codecool;

import java.util.List;

public abstract class Player {
    private Hand hand;
    protected String name;
    Player() {
        this.hand = new Hand();
    }

    public Card getTopCard() {
        return hand.getCardsOnHand().get(0);
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

    public abstract String chooseCardStatToCompare();

    public void removeTopCard(){
        hand.getCardsOnHand().remove(0);
    }
}
