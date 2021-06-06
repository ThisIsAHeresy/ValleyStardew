package com.company.Farms;

import com.company.Buildings.Building;

import java.util.ArrayList;

public class Farm {
    public int size;
    public ArrayList<Building> buildings;
    public double worth;

    public Farm(int size) {
        this.size = size;
        this.buildings = new ArrayList<Building>();
        this.worth = 10000.0;
    }

    public void addBuilding(Building building) {
        buildings.add(building);
        this.worth += building.cost;
    }
}
