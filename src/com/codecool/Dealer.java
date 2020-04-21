package com.codecool;

import java.util.List;
import java.util.Map;

public class Dealer extends Player {
    Dealer() {
        super();
        name = "Dealer";
    }
    public void addCardsToSideCards(List<Card> sideCards, List<Card> sideCardsToAdd) {
        for (Card card: sideCardsToAdd) {
            sideCards.add(card);
        }
    }
    public int chooseBestCardStatToCompare() { // TODO: 21.04.2020 think about changing way of working
        Card topCard = getTopCard();
        String[] stats = {"defence", "intelligence", "agility"};
        int biggestStat = topCard.getValueById("attack");
        String biggestStatName = "attack";
        for (String stat: stats) {
            int tempStat = topCard.getValueById(stat);
            if (tempStat > biggestStat) {
                biggestStat = tempStat;
                biggestStatName = stat;
            }
        }
        switch (biggestStatName) {
            case "attack":
                return 1;
            case "defence":
                return 2;
            case "intelligence":
                return 3;
            case "agility":
                return 4;
            default:
                return 0;
        }
    }
}
