package com.company;

import com.company.Buildings.Building;
import com.company.Farms.Farm;

import java.util.ArrayList;

public class Farmer {
    public double cash;
    public String name;
    public Farm farm;
    public ArrayList<Object> inventory;

    public Farmer(String name) {
        this.cash = 15000.0;
        this.name = name;
        this.inventory = new ArrayList<Object>();
    }
}
