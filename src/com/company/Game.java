package com.company;

import com.company.Farms.Farm;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    public int weeks;
    public int year;
    public ArrayList <Farmer> players;
    public ArrayList <Farm> farms;

    public void startGame() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String username = in.nextLine();
        Farmer newPlayer = new Farmer(username);
        players.add(newPlayer);
    }

    public void tick() {

    }
}
