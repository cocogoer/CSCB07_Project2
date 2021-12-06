package com.example.cscb07_project;

import java.io.Serializable;

public class Product implements Serializable {
    public String name;
    public double price;
    public int inventory_quantity;
    public String introduction;

    public Product (){

    }

    public Product(String name, double price, int inventory_quantity) {
        this.name = name;
        this.price = price;
        this.inventory_quantity = inventory_quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInventory_quantity() {
        return inventory_quantity;
    }

    public void setInventory_quantity(int inventory_quantity) {
        this.inventory_quantity = inventory_quantity;
    }
}
