package com.company.Plants;

import com.company.Farmer;

public class Potato extends Plant {
    public Potato() {
        super(0, 8, "Potato", 15, 0.33, 0.1, 20, 4, 11);
    }

    @Override
    public double yield(Farmer farmer) throws Exception {
        if (farmer.cash < this.collectionCostPerUnit * this.plantsPerUnit) {
            throw new Exception("You do not have enough money to collect the "+this.name+" plant!");
        }
        this.count = this.plantsPerUnit;
        farmer.inventory.add(this);
    }

    @Override
    public boolean sell(Farmer farmer) {
        if (this.growthStatus < this.maturityWeek) {
            farmer.cash += 0.9*this.sellPrice;
        } else {
            farmer.cash += this.sellPrice;
        }
        return true;
    }

    @Override
    public boolean purchase(Farmer farmer) {
        farmer.cash -= this.sellPrice;
        return true;
    }
}
