package com.company.Farms;

import com.company.Buildings.*;
import com.company.Farmer;
import com.company.Game;
import com.company.Plants.Plant;

import javax.lang.model.type.NullType;
import java.util.*;

public class Farm {
    public int size;
    public ArrayList<Building> buildings;
    public double baseWorth;
    public double worth;
    public Farmer owner;

    public Farm(int size) {
        this.size = size;
        this.buildings = new ArrayList<Building>();
        this.baseWorth = 10000.0;
    }

    public double getWorth() {
        return baseWorth + buildings.stream().mapToDouble(o -> o.baseWorth).sum();
    }

    public boolean tickFarm() {
        for (Building building:this.buildings) {
            if (building.getClass() == Field.class) {
                for (Plant plant:((Field) building).plants) {
                    this.owner.cash -= plant.protectionCost; // take protection cost for the week
                    plant.growthStatus += 1; // grow plant by one week
                }
            }
        }
        if (this.owner.cash >= 0) {  // If owner still has money return true
            return true;
        }
        return false;
    }

    public void addBuilding(Building building) throws Exception {
        if (buildings.stream().mapToInt(o -> o.size).sum() + building.size > this.size) {
            throw new Exception("This farm is too small to add yet another building!");
        }
        buildings.add(building);
    }

    public boolean purchase(Farmer farmer) throws Exception {
        if (farmer.cash < this.getWorth()) {
            throw new Exception("You don't have enough money to buy this farm!");
        }
        farmer.cash -= this.getWorth();
        return true;
    }

    public void buildingManager() {
        while (true) {
            Map<BuildingType, Building[]> buildingSizes = new HashMap<BuildingType, Building[]>();
            buildingSizes.put(BuildingType.BARN, new Building[]{new Barn(3), new Barn(5)});
            buildingSizes.put(BuildingType.FIELD, new Building[]{new Field(1), new Field(3)});
            buildingSizes.put(BuildingType.STABLES, new Building[]{new Stable(5)});
            buildingSizes.put(BuildingType.COOP, new Building[]{new Coop(2), new Coop(4)});
            buildingSizes.put(BuildingType.GREENHOUSE, new Building[]{new Greenhouse(4)});
            System.out.println("Choose type of a building (write its name):");
            for (BuildingType building : buildingSizes.keySet()) {
                System.out.println("* " + building.toString().toLowerCase());
            }
            System.out.println("* exit");
            Scanner in = new Scanner(System.in);
            String answer = in.nextLine().toUpperCase();
            Building[] building = null;
            try {
                building = buildingSizes.get(BuildingType.valueOf(answer));
            } catch (Exception e) {
                if (answer == "EXIT") {
                    return;
                } else {
                    System.out.println("Please provide a valid building name!");
                    continue;
                }
            }
            if (building.length == 1) {
                System.out.println("1. " + building[0].toString() + "($"+ building[0].getWorth() +")");
            } else {
                System.out.println("1. " + "Small "+ building[0].toString() + "($"+ building[0].getWorth() +")");
                System.out.println("2. " + "Big "+ building[1].toString() + "($"+ building[1].getWorth() +")");
            }
            System.out.println((building.length + 1) + ". Exit");
            answer = in.nextLine();
            Integer answerInt = null;
            try {
                answerInt = Integer.parseInt(answer);
            } catch (Exception e) {
                System.out.println("Provide a valid number!");
                continue;
            }
            if (answerInt == (building.length + 1)) {
                return;
            } else {
                if (answerInt > building.length || answerInt < 0) {
                    System.out.println("Provide a valid number!");
                } else {
                    if (building[answerInt].purchase(this.owner)) {
                        this.buildings.add(building[answerInt]);
                    } else {
                        System.out.println("You don't have enough money to buy this building!");
                    }
                    return;
                }
            }
        }
    }

    public void setOwner(Farmer newOwner) {
        this.owner = newOwner;
        newOwner.farm = this;
    }

    public void plantPlants(Game game) {
        System.out.println("Choose field to plant your plants on:");
        ArrayList<Field> fields = new ArrayList<Field>();
        for (Building building:this.buildings) {
            if (building.getClass() == Field.class) {
                System.out.println(fields.size()+1 + ". "+building.toString());
                fields.add((Field) building);
            }
        }
        System.out.println(fields.size()+1 + ". Exit");
        Scanner in = new Scanner(System.in);
        String answer = in.nextLine();
        int answerInt = 0;
        try {
            answerInt = Integer.parseInt(answer);
        } catch (Exception e) {
            System.out.println("Provide a valid number!");
            return;
        }
        if (answerInt == fields.size()+1 || answerInt < 0 || answerInt > fields.size()) {
            return;
        } else {
            Field field = fields.get(answerInt-1);
            System.out.println("Choose a plant to plant:");
            ArrayList<Plant> plants = new ArrayList<Plant>();
            for (Object item:this.owner.inventory) {
                if (Plant.class.isAssignableFrom(item.getClass())) {
                    System.out.println(plants.size()+1 + ". "+item.toString());
                    plants.add((Plant) item);
                }
            }
            System.out.println(plants.size()+1 + ". Exit");
            answer = in.nextLine();
            try {
                answerInt = Integer.parseInt(answer);
            } catch (Exception e) {
                System.out.println("Provide a valid number!");
                return;
            }
            if (answerInt == plants.size()+1 || answerInt < 0 || answerInt > plants.size()) {
                return;
            } else {
                try {
                    field.plantPlant(plants.get(answerInt-1), game, this.owner);
                } catch (Exception e) {
                    System.out.println(e.toString());
                    return;
                }
            }
        }
    }

    public void doTasks(Game game) {
        System.out.println(this.toString());
        System.out.println("You can do the following:\n" +
                "1. Buy buildings/fields\n" +
                "2. Sell buildings/fields\n" +
                "3. Plant plants\n" +
                "4. Collect yield\n" +
                "5. Exit");
        Scanner in = new Scanner(System.in);
        String answer = in.nextLine();
        if (answer.equals("1")) {
            this.buildingManager();
        } else if (answer.equals("2")) {
            this.buildingManagerSell();
        } else if (answer.equals("3")) {
            this.plantPlants(game);
        } else if (answer.equals("4")) {
            this.collectPlants();
        }
    }

    private void collectPlants() {
        for (Building building:this.buildings) {
            if (building.getClass() == Field.class) {
                for (Plant plant:((Field) building).plants) {
                    try {
                        plant.yield(this.owner);
                        ((Field) building).plants.remove(plant); // hope it doesn't crash like in python for changed list during iteration
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
        }
        System.out.println("Plants collected!");
    }

    private void buildingManagerSell() {
        System.out.println("You can sell the following buildings:");
        int x = 1;
        for (Building building:this.buildings) {
            System.out.println(x+ ". "+building.toString());
            x++;
        }
        System.out.println(x + ". Exit");
        Scanner in = new Scanner(System.in);
        String answer = in.nextLine();
        int answerInt = 0;
        try {
            answerInt = Integer.parseInt(answer);
        } catch (Exception e) {
            System.out.println("Provide a valid number!");
            return;
        }
        if (answerInt == x || answerInt < 0 || answerInt > x) {
            return;
        } else {
            buildings.get(answerInt-1).sell(this.owner);
            buildings.remove(buildings.get(answerInt-1));
        }
    }

    @Override
    public String toString() {
        return "Farm of size " +  size +
                ", with buildings " + buildings +
                ", worth " + getWorth() +
                " dollars";
    }
}
