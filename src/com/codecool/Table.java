package com.codecool;

import java.util.List;

public class Table {
    Deck deck;
    //List<Player> playerList;
    List<Card> sideCards;

    public Table(CardParser cardParser){
        this.deck = cardParser.getDeck();

    }

}
