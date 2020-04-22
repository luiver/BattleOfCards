package com.codecool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Table {
    private Deck deck;
    private List<Player> playerList;
    private List<Card> sideCards;
    private int numberOfPlayers;

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
        Player currentPlayer = dealer;
        Player opponentPlayer = player;


        while (hasDealerCards && hasPlayerCards){
            Card currentPlayerTopCard = currentPlayer.getTopCard();
            Card opponentPlayerTopCard = opponentPlayer.getTopCard();
            List<Card> cardsInPlay = new ArrayList<>();
            cardsInPlay.add(currentPlayerTopCard);
            cardsInPlay.add(opponentPlayerTopCard);

            //todo displayCurrentPlayerTopCard(Player currentPlayer);
            String choosenStat = currentPlayer.chooseCardStatToCompare();
            //todo displayBothPlayersTopCard(Player currentPlayer, Player opponentPlayer);
            int resultOfCompare = comparePlayersTopCards(choosenStat, currentPlayer, opponentPlayer); //todo create method adjust parameter
            if (resultOfCompare == 0) {
                addCardsToSideCards(cardsInPlay);
            } else {
                //todo whoTakesCards(currentPlayer, opponentPlayer, resultOfCompare); //create separate method
                if (resultOfCompare > 0) {
                    currentPlayer.addCardsToBottomOfHand(cardsInPlay);
                    if (sideCards.size() != 0) {
                        currentPlayer.addCardsToBottomOfHand(sideCards);
                    }
                } else {
                    opponentPlayer.addCardsToBottomOfHand(cardsInPlay);
                    if (sideCards.size() != 0) {
                        opponentPlayer.addCardsToBottomOfHand(sideCards);
                    }
                    //todo switchPlayers(currentPlayer, opponentPlayer);  //create separate method
                    if (currentPlayer == dealer){
                        currentPlayer = player;
                        opponentPlayer = dealer;
                    } else {
                        currentPlayer = dealer;
                        opponentPlayer = player;
                    }
                }
            }
            //todo clearTopCardsFromTable(); or //printTableWithoutTopCardsOnTable();
        }
        result = !hasPlayerCards ? dealer.getName() : player.getName();

        System.out.println("The Winner is :" + result);

    }

    private int comparePlayersTopCards(String choosenStat, Player currentPlayer, Player opponentPlayer) {
        Integer currentPlayerStat= currentPlayer.getTopCard().getValueById(choosenStat);
        Integer opponentPlayerStat = opponentPlayer.getTopCard().getValueById(choosenStat);
        int compare = currentPlayerStat.compareTo(opponentPlayerStat);
        return compare;
    }

    private void addCardsToSideCards(List<Card> cardsInPlay) {
        for (Card card : cardsInPlay) {
            sideCards.add(card);
        }
    }

}
