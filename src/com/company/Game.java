package com.company;

import com.company.Buildings.*;
import com.company.Farms.Farm;
import com.company.Plants.*;

import java.util.ArrayList;
import java.util.HashSet;
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
        System.out.println("1. Potato ($11)\n2. Carrot ($15)\n3. Onion ($30)\n4. Cabbage ($50)\n5. Cucumber ($55)\n6. Exit");
        Scanner in = new Scanner(System.in);
        String answer = in.nextLine();
        switch (answer) {
            case "1":
                this.buyItem(new Potato());
                break;
            case "2":
                this.buyItem(new Carrot());
                break;
            case "3":
                this.buyItem(new Onion());
                break;
            case "4":
                this.buyItem(new Cabbage());
                break;
            case "5":
                this.buyItem(new Cucumber());
                break;
            case "6":
                return;
        }
    }

    public void userChoice() {
        while (true) {
            System.out.println("Year " + this.year + ", week "+this.weeks + ". Current budget: "+this.players.get(0).cash);
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
                    return;
                case "9":
                    System.exit(0);
                default:
                    System.out.println("Please choose a valid action (number from 1 to 8)");
                    break;
            }
        }
    }

    private void checkStorage() {
        int x = 0;
        for (Object item:this.players.get(0).inventory) {
            System.out.println((x+1) + ". " + item.toString());
            x++;
        }
    }

    private void sellShop() {
        this.checkStorage();
        System.out.println("Which item would you like to sell?");
        Scanner in = new Scanner(System.in);
        String answer = in.nextLine();
        int answerInt = 0;
        try {
            answerInt = Integer.parseInt(answer);
        } catch (Exception e) {
            System.out.println("Provide a valid number!");
            return;
        }
        if (answerInt == this.players.get(0).inventory.size()+1 || answerInt < 1 || answerInt > this.players.get(0).inventory.size()) {
            return;
        } else {
            Plant item = (Plant) this.players.get(0).inventory.get(answerInt-1);
            item.sell(this.players.get(0));
        }
    }

    private void gameOver(String reason) {
        System.out.println("Game over! You have ran out of money "+reason);
        System.exit(0);
    }

    private void checkWinConditions() {
        int hectare = 0; // Other conditions not implemented yet
        HashSet<Class> typesOfItems = new HashSet<Class>();
        for (Farm farm:getFarmsOwnedByPlayer(this.players.get(0))) {
            for (Building building:farm.buildings) {
                if (building.type == BuildingType.FIELD) {
                    hectare += building.size * 5;
                }
            }
            for (Object object:this.players.get(0).inventory) {
                typesOfItems.add(object.getClass());
            }
        }
        if (hectare > 20 && typesOfItems.size() > 4) {
            System.out.println("Game over, you win! Congratulations!");
        }
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
        this.checkWinConditions();
    }
}
