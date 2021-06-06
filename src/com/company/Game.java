package com.company;

import com.company.Buildings.*;
import com.company.Farms.Farm;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

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

    public Farm generateFarm() {
        Farm farm = new Farm(ThreadLocalRandom.current().nextInt(10, 31));
        int building_count = ThreadLocalRandom.current().nextInt(0, 3);
        Building[] buildings = {new Barn(3), new Coop(3), new Field(2), new Greenhouse(3), new Stable(4)};
        for (int i = 0; i < building_count; i++) {
            farm.addBuilding(buildings[ThreadLocalRandom.current().nextInt(0, 5)]);
        }
        return farm;
    }

    public void userChoice() {
        while (true) {
            System.out.println("Take action:\n1. Buy a farm\n2. Buy/sell land field\n3. Buy new buildings\n4. Buy animals or plants\n5. Plant plants\n6. Collect yield\n7. Sell plants or animals\n8. Check storage\n9. View information about animals\n10. View information about plants\n11. Exit game");
            Scanner in = new Scanner(System.in);
            String answer = in.nextLine();
            switch (answer) {
                case "1":
                    break;
                case "2":
                    break;
                case "3":
                    break;
                case "4":
                    break;
                case "5":
                    break;
                case "6":
                    break;
                case "7":
                    break;
                case "8":
                    break;
                case "9":
                    break;
                case "10":
                    break;
                case "11":
                    System.exit(0);
                default:
                    System.out.println("Please choose a valid action (number from 1 to 11)");
                    break;
            }
        }
    }

    public void tick() {
        this.userChoice();

    }
}
