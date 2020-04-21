package com.codecool;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HumanPlayer extends Player {
    HumanPlayer() {
        super();
        Scanner scanner = new Scanner(System.in);
        name = scanner.nextLine();
    }
    public int chooseCardStatToCompare() { // TODO: 21.04.2020 somehow print stats names to user
        Map<Integer, String> statChoice = makeChoiceMap();
        boolean isRunning = true;
        Scanner scanner = new Scanner(System.in);
        while (isRunning) {
            int input = Integer.parseInt(scanner.nextLine());
            if (0 < input && input < 5) {
                scanner.close();
                return input;
            }
        }
        return 0;
    }

private Map<Integer, String> makeChoiceMap() {
    Map<Integer, String> statChoice = new HashMap<>();
    statChoice.put(1, "Attack");
    statChoice.put(2, "Defence");
    statChoice.put(3, "Intelligence");
    statChoice.put(4, "Agility");
    return statChoice;
}
}
