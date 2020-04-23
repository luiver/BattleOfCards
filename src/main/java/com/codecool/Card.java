package com.codecool;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Card {
    private String id;
    private String cardName;
    private Map<String, Integer> stats;

    public Card(String id, String cardName) {
        this.id = id;
        this.cardName = cardName;
        this.stats = new HashMap<>();
    }

    public void setCardStatsById(String id, int value) {
        this.stats.put(id, value);
    }

    public int getValueById(String id) {
        return this.stats.get(id);
    }

    public String getCardName() {
        return this.cardName;
    }
    public Map<String, Integer> getStats() {
        return this.stats;
    }
}
