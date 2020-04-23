package com.codecool;

import com.jakewharton.fliptables.FlipTable;

import java.util.ArrayList;
import java.util.Collections;
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
        printTable();
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

    private void createHumanPlayer() {
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
//        Player currentPlayer = dealer;
//        Player opponentPlayer = player;
        Player currentPlayer = player;
        Player opponentPlayer = dealer;

        while (hasDealerCards && hasPlayerCards){
            Card currentPlayerTopCard = currentPlayer.getTopCard();
            Card opponentPlayerTopCard = opponentPlayer.getTopCard();
            List<Card> cardsInPlay = new ArrayList<>();
            cardsInPlay.add(currentPlayerTopCard);
            cardsInPlay.add(opponentPlayerTopCard);
            printTopCards(currentPlayer, opponentPlayer); //todo function for debug delete after proper function added
            //todo displayCurrentPlayerTopCard(Player currentPlayer);
            String choosenStat = currentPlayer.chooseCardStatToCompare();
            //todo displayBothPlayersTopCard(Player currentPlayer, Player opponentPlayer);
            int resultOfCompare = comparePlayersTopCards(choosenStat, currentPlayer, opponentPlayer); //todo create method adjust parameter
            System.out.println(resultOfCompare + " - is result of compare");
            if (resultOfCompare == 0) {
                addCardsToSideCards(cardsInPlay);
                currentPlayer.removeTopCard();
                opponentPlayer.removeTopCard();
            } else {
                //todo whoTakesCards(currentPlayer, opponentPlayer, resultOfCompare); //create separate method
                if (resultOfCompare > 0) {
                    currentPlayer.addCardsToBottomOfHand(cardsInPlay);
                    currentPlayer.removeTopCard();
                    opponentPlayer.removeTopCard();
                    if (sideCards.size() != 0) {
                        currentPlayer.addCardsToBottomOfHand(sideCards);
                        sideCards.clear();
                    }
                } else {
                    opponentPlayer.addCardsToBottomOfHand(cardsInPlay);
                    currentPlayer.removeTopCard();
                    opponentPlayer.removeTopCard();
                    if (sideCards.size() != 0) {
                        opponentPlayer.addCardsToBottomOfHand(sideCards);
                        sideCards.clear();
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
            hasDealerCards = dealerHand.getIterator().hasNext();
            hasPlayerCards = playerHand.getIterator().hasNext();
            //todo clearTopCardsFromTable(); or //printTableWithoutTopCardsOnTable();
        }
        result = !hasPlayerCards ? dealer.getName() : player.getName();

        System.out.println("The Winner is: " + result);

    }

    private void printTopCards(Player currentPlayer, Player opponentPlayer) {
        currentPlayer.getHand().getCardsOnHand().forEach(e -> System.out.println(e.getCardName()));
        System.out.println("##############");
        opponentPlayer.getHand().getCardsOnHand().forEach(e -> System.out.println(e.getCardName()));
        String cards = currentPlayer.getTopCard().getCardName() + "############"+ opponentPlayer.getTopCard().getCardName() + "\n" +
            "attack " + currentPlayer.getTopCard().getValueById("attack") + "###############"+ "attack " + opponentPlayer.getTopCard().getValueById("attack") + "\n" +
            "defence " + currentPlayer.getTopCard().getValueById("defence") + "###########"+ "defence " + opponentPlayer.getTopCard().getValueById("defence") + "\n" +
            "intelligence " + currentPlayer.getTopCard().getValueById("intelligence") + "###########"+ "intelligence " + opponentPlayer.getTopCard().getValueById("intelligence") + "\n" +
            "agility " + currentPlayer.getTopCard().getValueById("agility") + "##############"+ "agility " + opponentPlayer.getTopCard().getValueById("agility") + "\n" ;

        System.out.println(cards);
    }

    private void printTable() {
        String[] leftHeaders = {"dupa1"};
        String[][] leftData = {};
        String left = FlipTable.of(leftHeaders, leftData);
        String[] midHeaders = {vLetter, sLetter};
        String[][] midData = { {"A", "B"} };
        String mid = FlipTable.of(midHeaders, midData);
        String[] righHeaders = {"DUPA1", "DUPA2"};
        String[][] rightData = { {"DUPA3", "DUPA4"}};
        String right = FlipTable.of(righHeaders, rightData);
        String[] headers = { "dealer", "side cards", "player" };
        String[][] data = //{
                { {left, mid, right} };
//                {"4", "5", "6"},
//        };
        System.out.println(FlipTable.of(headers, data));
    }

    String cardRevers = "╔══════════════════════════════╗\n" +
            "║                              ║\n" +
            "║    |\\                     /) ║\n" +
            "║  /\\_\\\\__               (_//  ║\n" +
            "║ |   `>\\-`     _._       //`) ║\n" +
            "║  \\ /` \\\\  _.-`:::`-._  //    ║\n" +
            "║   `    \\|`    :::    `|/     ║\n" +
            "║         |     :::     |      ║\n" +
            "║         |.....:::.....|      ║\n" +
            "║         |:::::::::::::|      ║\n" +
            "║         |     :::     |      ║\n" +
            "║         \\     :::     /      ║\n" +
            "║          \\    :::    /       ║\n" +
            "║           `-. ::: .-'        ║\n" +
            "║            //`:::`\\\\         ║\n" +
            "║           //   '   \\\\        ║\n" +
            "║          |/         \\\\       ║\n" +
            "║                              ║\n" +
            "╚══════════════════════════════╝";

    String vLetter = "8b           d8\n" +
            "`8b         d8'\n" +
            " `8b       d8' \n" +
            "  `8b     d8'  \n" +
            "   `8b   d8'   \n" +
            "    `8b d8'    \n" +
            "     `888'     \n" +
            "      `8'      ";

    String sLetter = " ad88888ba \n" +
            "d8\"     \"8b\n" +
            "Y8,        \n" +
            "`Y8aaaaa,  \n" +
            "  `\"\"\"\"\"8b,\n" +
            "        `8b\n" +
            "Y8a     a8P\n" +
            " \"Y88888P\" ";
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
