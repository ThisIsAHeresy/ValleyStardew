package com.company.Buildings;

import com.company.Farmer;
import com.company.Game;
import com.company.Plants.Plant;

import java.util.ArrayList;

public class Field extends Building {
    public ArrayList<Plant> plants;
    public Field(int size) {
        super(size, "Field", BuildingType.FIELD, 1000.0);  // Size of a field = 5 hectares
        this.plants = new ArrayList<Plant>();
    }

    public void plantPlant(Plant plant, Game game) throws Exception {
        if (this.plants.size() >= this.size * 5) {
            throw new Exception("Not enough of space on the field!");
        }
        if (!(plant.possibleToPlantWeek > game.weeks || plant.possibleToPlantWeekEnd < game.weeks)) {
            throw new Exception("This plant cannot be planted at this time of year!");
        }
        this.plants.add(plant);
    }

    @Override
    public double getWorth() {
        return this.baseWorth + ((size-1) * 400);
    }

    @Override
    public boolean sell(Farmer farmer) {
        farmer.cash += getWorth() * 0.9; // Don't recover entire cash
        return true;
    }

    @Override
    public boolean purchase(Farmer farmer) {
        if (farmer.cash < getWorth()) {
            return false;
        }
        farmer.cash -= getWorth();
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + " Space left (" + plants.size() + "/" + this.size * 5 + ")";
    }
}
