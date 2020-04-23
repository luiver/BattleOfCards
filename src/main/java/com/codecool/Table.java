package com.codecool;

import com.jakewharton.fliptables.FlipTable;
import com.codecool.Ui;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

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

    private void createHumanPlayer() {
        playerList.add(new HumanPlayer());
    }

    public void playGame(){
        switch (numberOfPlayers) {
            case 2:
                pvcMode();
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
            //printTopCards(currentPlayer, opponentPlayer); //todo function for debug delete after proper function added
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
        displayEndGameScreen(resultOfGame);
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

    private String getTopCardDisplay(Player player){
        String name = player.getTopCard().getCardName();
        String stat1 = "Attack: " + String.valueOf(player.getTopCard().getValueById("attack"));
        String stat2 = "Defence: " + String.valueOf(player.getTopCard().getValueById("defence"));
        String stat3 = "Intelligence: " + String.valueOf(player.getTopCard().getValueById("intelligence"));
        String stat4 = "Agility: " + String.valueOf(player.getTopCard().getValueById("agility"));

        String card = String.format("╔══════════════════════════════╗\n" +
                                    "║                              ║\n" +
                                    "║%-30s║\n" +
                                    "║                              ║\n" +
                                    "║                              ║\n" +
                                    "║%-30s║\n" +
                                    "║                              ║\n" +
                                    "║%-30s║\n" +
                                    "║                              ║\n" +
                                    "║%-30s║\n" +
                                    "║                              ║\n" +
                                    "║%-30s║\n" +
                                    "║                              ║\n" +
                                    "║                              ║\n" +
                                    "║                              ║\n" +
                                    "║                              ║\n" +
                                    "║                              ║\n" +
                                    "║                              ║\n" +
                                    "╚══════════════════════════════╝", name,stat1,stat2,stat3,stat4);
        return card;

    }

    private void printTable(Player currentPlayer, Player opponentPlayer, boolean isVisibleForBoth, boolean singleDisplay) {
        clearScreen();
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

    String cardRevers = Ui.getCardRevers();
    String emptyRevers = Ui.getEmptyRevers();
    String vLetter = Ui.getVLetter();
    String sLetter = Ui.getSLetter();

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

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    //todo move to UI
    private void displayEndGameScreen(String resultOfGame){
        System.out.println("The Winner is: " + resultOfGame + "\n");
        File file;
        if (resultOfGame.equals("Dealer")) {
            file = new File("data/looseScreen.txt");
        } else {
            file = new File("data/winScreen.txt");
        }
        String contents = new Scanner(file).useDelimiter("\\Z").next();
        System.out.println(contents);
    }
}
