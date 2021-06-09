package com.company.Plants;

import com.company.Farmer;

interface PlantMethods {
    public double yield(Farmer farmer) throws Exception;
    public boolean sell(Farmer farmer);
    public boolean purchase(Farmer farmer);
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
}
