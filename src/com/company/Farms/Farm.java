package com.company.Farms;

import com.company.Buildings.Building;
import com.company.Farmer;

import java.util.ArrayList;

public class Farm {
    public int size;
    public ArrayList<Building> buildings;
    public double worth;
    public Farmer owner;

    public Farm(int size) {
        this.size = size;
        this.buildings = new ArrayList<Building>();
        this.worth = 10000.0;
    }

    public void addBuilding(Building building) throws Exception {
        if (buildings.stream().mapToInt(o -> o.size).sum() + building.size > this.size) {
            throw new Exception("This farm is too small to add yet another building!");
        }
        buildings.add(building);
        this.worth += building.cost;
    }

    public boolean purchase(Farmer farmer) throws Exception {
        if (farmer.cash < this.worth) {
            throw new Exception("You don't have enough money to buy this farm!");
        }
        farmer.cash -= this.worth;
        return true;
    }

    public void setOwner(Farmer newOwner) {
        this.owner = newOwner;
    }

    @Override
    public String toString() {
        return "Farm of size " +  size +
                ", with buildings " + buildings +
                ", worth " + worth +
                " dollars";
    }
}
