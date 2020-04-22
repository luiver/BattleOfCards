package com.codecool;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HumanPlayer extends Player {
    HumanPlayer() {
        super();
        this.name = createPlayerName();
    }

    @Override
    public String chooseCardStatToCompare() { // TODO: 21.04.2020 somehow print stats names to user
        Map<Integer, String> statChoice = makeChoiceMap();
        boolean isRunning = true;
        Scanner scanner = new Scanner(System.in);
        int input = 0;
        while (isRunning) {
            printStatsToChoose(statChoice);
            input = Integer.parseInt(scanner.nextLine());
            if (0 < input && input < 5) {
                scanner.close();
                isRunning = false;
            }
        }
        return statChoice.get(input);
    }

    private void printStatsToChoose(Map<Integer, String> statChoice) {
        System.out.println("Choose your stat to play (1-4): ");
        statChoice.forEach((k,v) -> System.out.println(k+". "+v));
    }

    private Map<Integer, String> makeChoiceMap() {
        Map<Integer, String> statChoice = new HashMap<>();
        statChoice.put(1, "attack");
        statChoice.put(2, "defence");
        statChoice.put(3, "intelligence");
        statChoice.put(4, "agility");
        return statChoice;
    }

    private String createPlayerName() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your Nickname: ");
        name = scanner.nextLine();
        return name;
    }
}
