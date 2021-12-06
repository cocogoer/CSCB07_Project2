package com.example.cscb07_project.database;

import android.content.Context;

import com.example.cscb07_project.Global;

import java.util.List;

public class DataManager {

    //添加一条购物车数据
    //删除一条购物车数据
    //删除所有购物车数据
    //查询用户购物车数据
    private static DataManager instance = null;
    private Context context = null;

    public static synchronized DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public void init(Context context) {
        this.context = context;
        new NewsAppHelper(context).init();
    }

    //TODO 添加到购物车一条数据
    public boolean addShoppingRecord(ShoppingRecord data) {
        if (context == null) {
            return false;
        }

        return new NewsAppHelper(context).addShoppingRecord(data);
    }

    //仅仅需要修改的就是数量 先获取数量再进行操作
    public boolean alterShoppingRecord(ShoppingRecord data) {
        if (context == null) {
            return false;
        }
        //仅仅是为了方便，才在这加的
        data.setAccount(Global.account);
        return new NewsAppHelper(context).alterShoppingRecord(data);
    }

    public boolean deleteShoppingRecord(ShoppingRecord data) {
        if (context == null) {
            return false;
        }
        //仅仅是为了方便，才在这加的
        data.setAccount(Global.account);
        return new NewsAppHelper(context).deleteShoppingRecord(data);
    }


    public boolean deleteAllShoppingRecord(String account) {
        if (context == null) {
            return false;
        }
        return new NewsAppHelper(context).deleteAllShoppingRecord(account);
    }

    public List<ShoppingRecord> getShoppingRecord(ShoppingRecord data) {
        if (context == null) {
            return null;
        }
        data.setAccount(Global.account);
        return new NewsAppHelper(context).getShoppingRecord(data);
    }

    public List<ShoppingRecord> getAllShoppingRecord(String account) {
        if (context == null) {
            return null;
        }
        return new NewsAppHelper(context).getAllShoppingRecord(account);
    }


}
