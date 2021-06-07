package com.company.Buildings;

public enum BuildingType {
    FIELD, COOP, BARN, STABLES, GREENHOUSE
}

interface BuildingMethods {
    public double getWorth();
    public boolean sell();
    public boolean purchase();
}

public abstract class Building implements BuildingMethods {
    public int size;
    public String name;
    public BuildingType type;
    public double baseWorth;

    public Building(int size, String name, BuildingType type, Double baseWorth) {
        this.size = size;
        this.name = name;
        this.type = type;
        this.baseWorth = baseWorth;
    }

    @Override
    public String toString() {
        return name + " of size " + size + ".";
    }
}
