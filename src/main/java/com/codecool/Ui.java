package com.codecool;

import com.jakewharton.fliptables.FlipTable;

import java.util.Scanner;

public class Ui {

    private void storyScreen() {
        String left = " X ";
        String mid = "In this game you will encounter mighty \nand scary enemy called Dealer!\nIf you are brave enough write Your name!";
        String right = " X ";
        String[] headers = {" X ", "Welcome to Battle of Cards game!", " X "};
        String[][] data =
                {{left, mid, right}};
        System.out.println(FlipTable.of(headers, data));
    }

    public void Menu() {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            printMenu();
            int input = Integer.parseInt(scanner.nextLine());
            switch (input) {
                case 1:
                    storyScreen();
                    isRunning = false;
                    break;
                case 2:
                    helpScreen();
                    break;
                case 3:
                    System.exit(0);
            }
        }
    }

    private void printMenu() {
        String[] header = {"Menu"};
        String[][] menu = {{"1. Start game"}, {"2.Help"}, {"3.Exit"}};
        System.out.println(FlipTable.of(header, menu));
    }

    private void helpScreen() {
        String[] header = {"Help"};
        String[][] menu = {{"In this game You are fighting with opponent - Dealer, using Your cards.\n" +
                "Each card has 4 statistics: Attack, Defense, Intelligence and Agility.\n" +
                "When it is Your turn You can choose which statistic do You want to fight with.\n" +
                "Player with higher statistics value wins a round and takes cards from table.\n" +
                "Game ends when one of the players runs out of the cards."}};
        System.out.println(FlipTable.of(header, menu));
    }

    public static String getCardRevers() {

        String cardRevers = "╔══════════════════════════════╗\n" +
                "║                              ║\n" +
                "║    |\\                     /) ║\n" +
                "║  /\\_\\\\__               (_//  ║\n" +
                "║ |   `>\\-`     _._       //`) ║\n" +
                "║  \\ /` \\\\  _.-`:::`-._  //    ║\n" +
                "║   `    \\|`    :::    `|/     ║\n" +
                "║         |     :::     |      ║\n" +
                "║         |.....:::.....|      ║\n" +
                "║         |:::::::::::::|      ║\n" +
                "║         |     :::     |      ║\n" +
                "║         \\     :::     /      ║\n" +
                "║          \\    :::    /       ║\n" +
                "║           `-. ::: .-'        ║\n" +
                "║            //`:::`\\\\         ║\n" +
                "║           //   '   \\\\        ║\n" +
                "║          |/         \\\\       ║\n" +
                "║                              ║\n" +
                "╚══════════════════════════════╝";
        return cardRevers;
    }

    public static String getEmptyRevers() {
        String emptyRevers = "╔══════════════════════════════╗\n" +
                "║                              ║\n" +
                "║                              ║\n" +
                "║                              ║\n" +
                "║                              ║\n" +
                "║                              ║\n" +
                "║                              ║\n" +
                "║                              ║\n" +
                "║                              ║\n" +
                "║                              ║\n" +
                "║                              ║\n" +
                "║                              ║\n" +
                "║                              ║\n" +
                "║                              ║\n" +
                "║                              ║\n" +
                "║                              ║\n" +
                "║                              ║\n" +
                "║                              ║\n" +
                "╚══════════════════════════════╝";
        return emptyRevers;
    }

    public static String getVLetter() {
        String vLetter = "        8b           d8\n" +
                "        `8b         d8'\n" +
                "         `8b       d8' \n" +
                "          `8b     d8'  \n" +
                "           `8b   d8'   \n" +
                "            `8b d8'    \n" +
                "             `888'     \n" +
                "              `8'      ";
        return vLetter;
    }

    public static String getSLetter() {
        String sLetter = "          ad88888ba \n" +
                "         d8\"     \"8b\n" +
                "           Y8,        \n" +
                "           `Y8aaaaa,  \n" +
                "              `\"\"\"\"\"8b,\n" +
                "                   `8b\n" +
                "           Y8a     a8P\n" +
                "            \"Y88888P\" ";
        return sLetter;
    }
}
