package com.example.cscb07_project;


import androidx.annotation.NonNull;

import java.io.Serializable;

public class CustomProductView implements Serializable {

    private String storeName;//产品所属店铺

    private String ProductName;
    //product的名字
    private Integer ProductQuantity;
    //product的数量
    private Integer ProductPrice;

    public CustomProductView(String storeName,String ProductName, Integer ProductQuantity, Integer ProductPrice) {
        this.storeName = storeName;
        this.ProductName = ProductName;
        this.ProductQuantity = ProductQuantity;
        this.ProductPrice = ProductPrice;
    }


    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public Integer getProductQuantity() {
        return ProductQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        ProductQuantity = productQuantity;
    }

    public Integer getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(Integer productPrice) {
        ProductPrice = productPrice;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
