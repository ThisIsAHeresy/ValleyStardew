package com.company.Farms;

import com.company.Buildings.*;
import com.company.Farmer;

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
        Map<BuildingType, Building[]> buildingSizes = new HashMap<BuildingType, Building[]>();
        buildingSizes.put(BuildingType.BARN,        new Building[]{new Barn(3), new Barn(5)});
        buildingSizes.put(BuildingType.FIELD,       new Building[]{new Field(1), new Field(3)});
        buildingSizes.put(BuildingType.STABLES,     new Building[]{new Stable(5)});
        buildingSizes.put(BuildingType.COOP,        new Building[]{new Coop(2), new Coop(4)});
        buildingSizes.put(BuildingType.GREENHOUSE,  new Building[]{new Greenhouse(4)});
        System.out.println("Choose type of a building:");
        for (BuildingType building:buildingSizes.keySet()) {
            building.toString().to
        }

        for (Map.Entry<BuildingType, Building[]> building:buildingSizes.entrySet()) {

        }
    }

    public void setOwner(Farmer newOwner) {
        this.owner = newOwner;
        newOwner.farm = this;
    }

    public void doTasks() {
        System.out.println(this.toString());
        System.out.println("You can do the following:\n" +
                "1. Buy buildings/fields\n" +
                "2. Plant plants\n" +
                "3. Collect yield");
        Scanner in = new Scanner(System.in);
        String answer = in.nextLine();

    }

    @Override
    public String toString() {
        return "Farm of size " +  size +
                ", with buildings " + buildings +
                ", worth " + getWorth() +
                " dollars";
    }
}
