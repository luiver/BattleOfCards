package com.codecool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Table {
    Deck deck;
    List<Player> playerList;
    List<Card> sideCards;

    public Table(CardParser cardParser) {
        this.deck = cardParser.getDeck();
        this.sideCards = new ArrayList<>();
        this.playerList = new ArrayList<>();
        playerList.add(new Dealer());
        createHumanPlayer();
    }

    public Deck getDeck() {
        return deck;
    }

    public List<Card> getSideCards() {
        return sideCards;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void shuffleDeck() {
        Collections.shuffle(deck.getCards());
    }

    public void playCards() {
        int numberOfPlayers = playerList.size();
        int cardsInDeck = deck.getCards().size();
        int numberOfCardsForPlayers = cardsInDeck % numberOfPlayers == 0 ? cardsInDeck : cardsInDeck - (cardsInDeck % numberOfPlayers);

        if (numberOfPlayers % 2 == 0) {
            for (int i = 0; i < numberOfCardsForPlayers; i++) {
                if (i % 2 == 0) {
                    playerList.get(0).getHand().addCard(deck.getCards().get(i));
                } else {
                    playerList.get(1).getHand().addCard(deck.getCards().get(i));
                }
            }
        }
        //todo option for more players

    }

    public void createHumanPlayer() {
        playerList.add(new HumanPlayer());
    }
}
