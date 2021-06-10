package com.company;

public class Main {
    // Not everything is implemented.
    public static void main(String[] args) {
        Game game = new Game(1, 2020);
        game.startGame();
        while (true) {
            game.tick();
        }
    }
}
