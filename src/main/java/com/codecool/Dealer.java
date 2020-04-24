package com.codecool;

import java.util.Collections;
import java.util.Map;

public class Dealer extends Player {
    Dealer() {
        name = "Dealer";
    }

    @Override
    public String chooseCardStatToCompare() {
        Card topCard = getTopCard();
        Map<String, Integer> stats = topCard.getStats();
        String biggestValue = Collections.max(stats.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey();
        System.out.println("Dealer is choosing a stat to play");
        try { Thread.sleep(3000); }
        catch(InterruptedException ex) {Thread.currentThread().interrupt();}
        return biggestValue;
    }
}
