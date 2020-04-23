package com.codecool;

import com.github.tomaslanger.chalk.Chalk;
import com.jakewharton.fliptables.FlipTable;

public class Main {

    public static void main(String[] args) {

        Ui ui = new Ui();
        ui.Menu();
        Table table = new Table(new CardParser());

        table.shuffleDeck();
        table.playCards();
        table.playGame();

    }
}
