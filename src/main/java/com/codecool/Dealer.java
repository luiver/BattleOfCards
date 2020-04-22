package com.codecool;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Dealer extends Player {
    Dealer() {
        super();
        name = "Dealer";
    }

    public void addCardsToSideCards(List<Card> sideCards, List<Card> sideCardsToAdd) {
        for (Card card : sideCardsToAdd) {
            sideCards.add(card);
        }
    }

    @Override
    public String chooseCardStatToCompare() {
        Card topCard = getTopCard();
        Map<String, Integer> stats = topCard.getStats();
        String biggestValue = Collections.max(stats.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey();
//        int biggest = stats.entrySet().stream().max((entry1, entry2) -> entry1.getValue() - entry2.getValue()).get().getValue();
        return biggestValue;
    }
}
