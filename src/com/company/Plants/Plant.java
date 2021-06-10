package com.company.Plants;

import com.company.Farmer;

interface PlantMethods {
    public void yield(Farmer farmer) throws Exception;
    public boolean sell(Farmer farmer);
    public boolean purchase(Farmer farmer) throws Exception;
}

public abstract class Plant implements PlantMethods {
    public final int possibleToPlantWeek;
    public final int possibleToPlantWeekEnd;
    public final String name;
    public final int maturityWeek;
    public int growthStatus;
    public final double preparationCost;
    public final double protectionCost;
    public final int plantsPerUnit;
    public final double collectionCostPerUnit;
    public final double sellPrice;
    public int count;


    public Plant(int possibleToPlantWeek, int possibleToPlantWeekEnd, String name, int maturityWeek, double preparationCost, double protectionCost, int plantsPerUnit, double collectionCostPerUnit, double sellPrice) {
        this.possibleToPlantWeek = possibleToPlantWeek;
        this.possibleToPlantWeekEnd = possibleToPlantWeekEnd;
        this.name = name;
        this.maturityWeek = maturityWeek;
        this.growthStatus = 0;
        this.preparationCost = preparationCost;
        this.protectionCost = protectionCost;
        this.plantsPerUnit = plantsPerUnit;
        this.collectionCostPerUnit = collectionCostPerUnit;
        this.sellPrice = sellPrice;
        this.count = 1;
    }

    public void yield(Farmer farmer) throws Exception {
        if (farmer.cash < this.collectionCostPerUnit * this.plantsPerUnit) {
            throw new Exception("You do not have enough money to collect the "+this.name+" plant!");
        }
        if (this.growthStatus < this.maturityWeek) {
            throw new Exception("This plant is not ready to be collected yet!");
        }
        this.count = this.plantsPerUnit;
        farmer.inventory.add(this);
    }

    public boolean sell(Farmer farmer) {
        if (this.growthStatus < this.maturityWeek) {
            farmer.cash += 0.9*this.sellPrice;
        } else {
            farmer.cash += this.sellPrice;
        }
        return true;
    }

    public boolean purchase(Farmer farmer) throws Exception {
        if (farmer.cash < this.sellPrice) {
            throw new Exception("You don't have enough money to buy this plant!");
        }
        farmer.cash -= this.sellPrice;
        farmer.inventory.add(this);
        return true;
    }

    @Override
    public String toString() {
        return name + " ("+count+"), worth: " + sellPrice + ", ";
    }
}
