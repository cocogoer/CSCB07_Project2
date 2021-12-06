package com.example.cscb07_project;

import java.io.Serializable;
import java.util.List;

//每一项的数据项
public class CustomStoreView implements Serializable {
    //public List<Store> storeList;
    //store的列表
    public String StoreName;
    //store的名字
    //store the information that which customer order products from which store
    public String StoreOwner;
    //store的owner

    public CustomStoreView(String StoreName, String StoreOwner){

        this.StoreName = StoreName;
        this.StoreOwner = StoreOwner;
    }


    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public String getStoreOwner() {
        return StoreOwner;
    }

    public void setStoreOwner(String storeOwner) {
        StoreOwner = storeOwner;
    }

}
