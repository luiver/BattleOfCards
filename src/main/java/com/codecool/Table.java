package com.codecool;

import com.github.tomaslanger.chalk.Chalk;
import com.jakewharton.fliptables.FlipTable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Table {
    private Deck deck;
    private List<Player> playerList;
    private List<Card> sideCards;
    private int numberOfPlayers;
    private Ui ui;
    private String cardRevers;
    private String emptyRevers;
    private String vLetter;
    private String sLetter;

    public Table(CardParser cardParser) {
        initializeUI();
        initializeTableComponents(cardParser);
    }

    private void initializeUI() {
        this.ui = new Ui();
        cardRevers = ui.getCardRevers();
        emptyRevers = ui.getEmptyRevers();
        vLetter = ui.getVLetter();
        sLetter = ui.getSLetter();
    }

    private void initializeTableComponents(CardParser cardParser){
        this.deck = cardParser.getDeck();
        this.sideCards = new ArrayList<>();
        this.playerList = new ArrayList<>();
        playerList.add(new Dealer());
        createHumanPlayer();
        numberOfPlayers = playerList.size();
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
    }

    private void createHumanPlayer() {
        playerList.add(new HumanPlayer());
    }

    public void playGame(){
        switch (numberOfPlayers) {
            case 2:
                pvcMode();
                break;
            case 3:
                //dealerVs2Players //todo
                break;
            case 4:
                //dealerVs3Players //todo
                break;
            default:
                //exit //todo
        }
    }

    private void pvcMode(){
        Player dealer = getPlayerList().get(0);
        Player player = getPlayerList().get(1);
        boolean hasDealerCards = dealer.getHand().getIterator().hasNext();
        boolean hasPlayerCards = player.getHand().getIterator().hasNext();
        Player currentPlayer = player;
        Player opponentPlayer = dealer;
        while (hasDealerCards && hasPlayerCards){
            List<Card> cardsInPlay = new ArrayList<>();
            cardsInPlay.add(currentPlayer.getTopCard());
            cardsInPlay.add(opponentPlayer.getTopCard());
            printTable(currentPlayer,opponentPlayer, false, true);
            String choosenStat = currentPlayer.chooseCardStatToCompare();
            printTable(currentPlayer,opponentPlayer, true, false);
            int resultOfCompare = comparePlayersTopCards(choosenStat, currentPlayer, opponentPlayer);
            printResultOfCompare(resultOfCompare, currentPlayer, opponentPlayer);
            boolean isSwitch = doSwitchPlayers( currentPlayer,  opponentPlayer,  resultOfCompare, cardsInPlay);
            if (isSwitch) {
                if (currentPlayer == dealer){
                    currentPlayer = player;
                    opponentPlayer = dealer;
                } else {
                    currentPlayer = dealer;
                    opponentPlayer = player;
                }
            }
            hasDealerCards = dealer.getHand().getIterator().hasNext();
            hasPlayerCards = player.getHand().getIterator().hasNext();
            printTable(currentPlayer,opponentPlayer, false, false);
            printEndTurn();
        }
        String resultOfGame = !hasPlayerCards ? dealer.getName() : player.getName();
        ui.displayEndGameScreen(resultOfGame);
    }

    private boolean doSwitchPlayers(Player currentPlayer, Player opponentPlayer, int resultOfCompare, List<Card> cardsInPlay) {
        if (resultOfCompare == 0) {
            addCardsToSideCards(cardsInPlay);
            currentPlayer.removeTopCard();
            opponentPlayer.removeTopCard();
        } else {
            if (resultOfCompare > 0) {
                currentPlayer.addCardsToBottomOfHand(cardsInPlay);
                currentPlayer.removeTopCard();
                opponentPlayer.removeTopCard();
                if (sideCards.size() != 0) {
                    currentPlayer.addCardsToBottomOfHand(sideCards);
                    sideCards.clear();
                }return false;
            } else {
                opponentPlayer.addCardsToBottomOfHand(cardsInPlay);
                currentPlayer.removeTopCard();
                opponentPlayer.removeTopCard();
                if (sideCards.size() != 0) {
                    opponentPlayer.addCardsToBottomOfHand(sideCards);
                    sideCards.clear();
                }return true;
            }
        }return false;
    }

    private void printEndTurn(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press any key to end turn");
        scanner.nextLine();
    }

    private void printResultOfCompare(int resultOfCompare, Player cp, Player op){
        if(resultOfCompare == 0){
            System.out.println("Tie! Cards will go to SideCards"); //todo adds colors green to win red to loose white for tie
        } else if (resultOfCompare == 1){
            System.out.println(cp.name+" win that round, and gather all cards");
        } else {
            System.out.println(op.name+" wins that round, and gather all cards");
        }
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    private String getTopCardDisplay(Player player){
        String name = "" +Chalk.on(player.getTopCard().getCardName()).green().bold();
        String stat1 = "" +Chalk.on("Attack: " + (player.getTopCard().getValueById("attack"))).red();
        String stat2 = "" +Chalk.on("Defence: " + (player.getTopCard().getValueById("defence"))).cyan();
        String stat3 = "" +Chalk.on("Intelligence: " + (player.getTopCard().getValueById("intelligence"))).blue();
        String stat4 = "" +Chalk.on("Agility: " + (player.getTopCard().getValueById("agility"))).magenta();
        return ui.createTopCard(name,stat1,stat2,stat3,stat4);
    }

    private void printTable(Player currentPlayer, Player opponentPlayer, boolean isVisibleForBoth, boolean singleDisplay) {
        ui.clearScreen();
        String cpCardToDisplay;
        String opCardToDisplay;
        Player dealer = playerList.get(0);
        if (!isVisibleForBoth && singleDisplay){
            if (currentPlayer == dealer) {
                cpCardToDisplay = emptyRevers;
                opCardToDisplay = getTopCardDisplay(currentPlayer);
            } else {
                cpCardToDisplay = getTopCardDisplay(currentPlayer);
                opCardToDisplay = emptyRevers;
            }
        } else if (isVisibleForBoth && !singleDisplay) {
            if (currentPlayer == dealer) {
                cpCardToDisplay = getTopCardDisplay(opponentPlayer);
                opCardToDisplay = getTopCardDisplay(currentPlayer);
            } else {
                cpCardToDisplay = getTopCardDisplay(currentPlayer);
                opCardToDisplay = getTopCardDisplay(opponentPlayer);
            }
        } else {
            cpCardToDisplay = emptyRevers;
            opCardToDisplay = emptyRevers;
        }

        String isReverseOnPlayerHand = playerList.get(1).getHand().getCardsOnHand().size()>1 ? cardRevers : emptyRevers;
        String isReverseOnOponentHand = playerList.get(0).getHand().getCardsOnHand().size()>1 ? cardRevers : emptyRevers;
        String isReverseOnSideCards = sideCards.size() > 0 ? cardRevers : emptyRevers ;

        String[] leftHeaders = {"Hand", "Top Card"};
        String[][] leftData = {{isReverseOnOponentHand, opCardToDisplay}};
        String left = FlipTable.of(leftHeaders, leftData);
        String[] midHeaders = {vLetter, sLetter};
        String[][] midData = { {isReverseOnSideCards, isReverseOnSideCards} };
        String mid = FlipTable.of(midHeaders, midData);
        String[] righHeaders = {"Top Card", "Hand"};
        String[][] rightData = { {cpCardToDisplay, isReverseOnPlayerHand}};
        String right = FlipTable.of(righHeaders, rightData);
        String[] headers = { playerList.get(0).getName(), "side cards", playerList.get(1).getName() };
        String[][] data = { {left, mid, right} };

        System.out.println(FlipTable.of(headers, data));
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
