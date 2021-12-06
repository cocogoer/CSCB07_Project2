package com.example.cscb07_project;

import java.util.ArrayList;
import java.util.List;

public class Store {
    public String storeName;
    public List<Product> products_inventory;
    public List<Order> orders;

    public String owner;
    public String ownerId;

    public Store(String storeName){
        this.storeName = storeName;
        products_inventory = new ArrayList<Product>();
        orders = new ArrayList<Order>();
    }

    public Store() {
        products_inventory = new ArrayList<Product>();

    }
    public String toString() {
        return storeName;
    }
}
