package com.company;

import com.company.Buildings.*;
import com.company.Farms.Farm;
import com.company.Plants.Plant;
import com.company.Plants.Potato;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Game {
    public int weeks;
    public int year;
    public ArrayList <Farmer> players;
    public ArrayList <Farm> farms;

    public Game(int weeks, int year) {
        this.weeks = weeks;
        this.year = year;
        this.players = new ArrayList<Farmer>();
        this.farms = new ArrayList<Farm>();
    }

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
            try {
                farm.addBuilding(buildings[ThreadLocalRandom.current().nextInt(0, 5)]);
            } catch (Exception e) { // Should never happen but ¯\_(ツ)_/¯
                continue;
            }
        }
        return farm;
    }

    public ArrayList <Farm> getFarmsOwnedByPlayer(Farmer owner) {
        ArrayList <Farm> farms = new ArrayList<Farm>();
        for (Farm farm:this.farms) {
            if (farm.owner == owner) {
                farms.add(farm);
            }
        }
        return farms;
    }

    public void buyingFarm() {
        Farm[] farms = {generateFarm(), generateFarm(), generateFarm()};
        Scanner in = new Scanner(System.in);
        System.out.println("Choose one of the available farms below: ");
        for (int i = 0; i < 3; i++) {
            System.out.println(i+1 + ". " + farms[i].toString());
        }
        System.out.println("4. Exit");
        String answer = in.nextLine();
        if ("123".contains(answer) && answer.length() == 1) {
            Farm chosen_farm = farms[Integer.parseInt(answer) - 1];
            try {
                if (chosen_farm.purchase(this.players.get(0))) {
                    chosen_farm.setOwner(this.players.get(0));
                    this.farms.add(chosen_farm);
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }

    public void buyItem(Plant item) {
        Farmer owner = this.players.get(0);
        try {
            item.purchase(owner);
        } catch (Exception e) {
            System.out.println(e.toString());
            return;
        }
        System.out.println("Item bought successfully!");

    }
    
    public void shop() {
        System.out.println("Choose what to buy:");
        System.out.println("1. Potato ($11)\n2. Exit");
        Scanner in = new Scanner(System.in);
        String answer = in.nextLine();
        switch (answer) {
            case "1":
                this.buyItem(new Potato());
                break;
            case "2":
                return;
        }
    }

    public void userChoice() {
        while (true) {
            System.out.println("Take action:\n1. Buy a farm\n2. Manage farm\n3. Buy animals or plants\n4. Sell plants or animals\n5. Check storage\n6. View information about animals\n7. View information about plants\n8. Next week\n9. Exit game");
            Scanner in = new Scanner(System.in);
            String answer = in.nextLine();
            switch (answer) {
                case "1":
                    this.buyingFarm();
                case "2":
                    Farm farm = null;
                    try {
                        farm = getFarmsOwnedByPlayer(this.players.get(0)).get(0);
                    } catch (Exception e) {
                        System.out.println("You don't have any farms!");
                    }
                    try {
                        farm.doTasks(this);
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                    break;
                case "3":
                    this.shop();
                    break;
                case "4":
                    this.sellShop();
                    break;
                case "5":
                    this.checkStorage();
                    break;
                case "6":
                    break;
                case "7":
                    break;
                case "8":
                    break;
                case "9":
                    System.exit(0);
                default:
                    System.out.println("Please choose a valid action (number from 1 to 8)");
                    break;
            }
        }
    }

    private void checkStorage() {
        for (Object item:this.players.get(0).inventory) {
            System.out.println(item.toString());
        }
    }

    private void sellShop() {
    }

    private void gameOver(String reason) {
        System.out.println("Game over! You have ran out of money "+reason);
        System.exit(0);
    }

    public void tick() {
        this.userChoice();
        for (Farm farm:this.farms) {
            Farmer owner = farm.owner;
            if (!(farm.tickFarm())) {
                this.gameOver("while keeping the farm safe.");
            }
        }
        if (this.weeks > 52) {
            this.year++;
            this.weeks = 1;
        } else {
            this.weeks++;
        }
    }
}
