package com.company.Buildings;

enum BuildingType {
    FIELD, COOP, BARN, STABLES, GREENHOUSE
}

public abstract class Building {
    public int size;
    public String name;
    public BuildingType type;
    public double cost;

    public Building(int size, String name, BuildingType type, Double cost) {
        this.size = size;
        this.name = name;
        this.type = type;
        this.cost = cost;
    }

}
