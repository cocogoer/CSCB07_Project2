package com.example.cscb07_project;

import java.io.PushbackInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderInfo implements Serializable {
    private List<CustomProductView> data;

    public String storeName;
    public String status;
    public String customerName;
    public String customerId;
    public ArrayList<Product> productList;
    public String orderId;

    public OrderInfo (){

    }

    public OrderInfo(String storeName, String status, String customerName, String customerId, ArrayList<Product> productList, String orderId) {
        this.storeName = storeName;
        this.status = status;
        this.customerId = customerId;
        this.customerName = customerName;
        this.productList = productList;
        this.orderId = orderId;
    }

    public OrderInfo(String storeName, String status, String customerName) {
        this.storeName = storeName;
        this.status = status;
        this.customerName = customerName;
    }

    public List<CustomProductView> getData() {
        return data;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
