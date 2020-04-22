package com.codecool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Table {
    Deck deck;
    List<Player> playerList;
    List<Card> sideCards;
    int numberOfPlayers = 0;

    public Table(CardParser cardParser) {
        this.deck = cardParser.getDeck();
        this.sideCards = new ArrayList<>();
        this.playerList = new ArrayList<>();
        playerList.add(new Dealer());
        createHumanPlayer();
        numberOfPlayers = playerList.size();
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

    public void playGame(){
        switch (numberOfPlayers) {
            case 2:
                dealerVsPlayer();
                break;
            case 3:
                //dealerVs2Players
                break;
            case 4:
                //dealerVs3Players
                break;
            default:
                //exit?
        }
    }

    private void dealerVsPlayer(){
        Player dealer = getPlayerList().get(0);
        Player player = getPlayerList().get(1);
        Hand dealerHand = dealer.getHand();
        Hand playerHand = player.getHand();
        boolean hasDealerCards = dealerHand.getIterator().hasNext();
        boolean hasPlayerCards = playerHand.getIterator().hasNext();
        String result;

        while (hasDealerCards && hasPlayerCards){
            //todo do while both players have not empty hands, carry on turns and compare cards

        }
        result = !hasPlayerCards ? dealer.getName() : player.getName();

        System.out.println("The Winner is :" + result);
    }
}
