package com.company.Buildings;

import com.company.Farmer;

public class Coop extends Building {
    public Coop(int size) {
        super(size, "Coop", BuildingType.COOP, 2000.0);
    }

    @Override
    public double getWorth() {
        return this.baseWorth + (size * 400);
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
}
