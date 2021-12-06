package com.example.cscb07_project.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NewsAppHelper extends SQLiteOpenHelper {

    private Context context;


    //暂时先这么建表，把店铺名和商品名当作外键，实际上不能这么做
    public static final String CREATE_SHOPPING_CAR = "create table if not exists shopping_car("
            + "shoppingRecordId integer primary key autoincrement,"
            + "account text,"
            + "amount Integer,"
            + "productName text,"
            + "storeName text,"
            + "productPrice Integer"
            + ")";


    public NewsAppHelper(@Nullable Context context) {
        super(context, "ShoppingCar.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void init() {
        try (SQLiteDatabase db = this.getReadableDatabase()) {
            db.execSQL(CREATE_SHOPPING_CAR);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //用户表操作
    //TODO 添加到购物车一条数据
    //数量、店铺名、产品名、产品价格
    public boolean addShoppingRecord(ShoppingRecord data) {
        String sql = "insert into shopping_car values("
                + null + ","
                + "'" + data.getAccount() + "',"
                + data.getProductAmount() + ","
                + "'" + data.getProductInfo().getProductName() + "',"
                + "'" + data.getProductInfo().getStoreName() + "',"
                + data.getProductInfo().getProductPrice()
                + ")";
        return doSQL(sql);
    }

    //仅仅需要修改的就是数量 先获取数量再进行操作
    public boolean alterShoppingRecord(ShoppingRecord data) {
        String sql = "update shopping_car set amount = "
                + "'" + data.getProductAmount() + "' "
                + "where storeName = '" + data.getProductInfo().getStoreName() + "'"
                + " and account = '" + data.getAccount() + "'"
                + " and productName = '" + data.getProductInfo().getProductName() + "'";
        return doSQL(sql);
    }

    public boolean deleteShoppingRecord(ShoppingRecord data) {
        String sql = "delete from shopping_car "
                + "where storeName = '" + data.getProductInfo().getStoreName() + "'"
                + " and account = '" + data.getAccount() + "'"
                + "and productName = '" + data.getProductInfo().getProductName() + "'";
        return doSQL(sql);
    }

    //删除所有数据（提交购物车使用）
    public boolean deleteAllShoppingRecord(String account) {
        String sql = "delete from shopping_car where account = "
                + "'" + account + "'";
        return doSQL(sql);
    }

    @SuppressLint("Range")
    public List<ShoppingRecord> getShoppingRecord(ShoppingRecord data) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<ShoppingRecord> list = new ArrayList<>();
        String sql = "select * from shopping_car where account = '" + data.getAccount() + "'"
                + " and storeName = '" + data.getProductInfo().getStoreName() + "'"
                + " and productName = '" + data.getProductInfo().getProductName() + "'";
        Cursor cur = null;
        try {
            cur = db.rawQuery(sql, null);
        } catch (Exception e) {
            db.close();
            throw e;
        }
        if (cur != null) {
            if (cur.getCount() > 0) {
                cur.moveToFirst();
                do {
                    ShoppingRecord shoppingRecord = new ShoppingRecord();
                    shoppingRecord.setRecordId(cur.getInt(cur.getColumnIndex("shoppingRecordId")));
                    shoppingRecord.setAccount(cur.getString(cur.getColumnIndex("account")));
                    shoppingRecord.setProductAmount(cur.getInt(cur.getColumnIndex("amount")));
                    ProductInfo productInfo = new ProductInfo();
                    productInfo.setProductName(cur.getString(cur.getColumnIndex("productName")));
                    productInfo.setStoreName(cur.getString(cur.getColumnIndex("storeName")));
                    productInfo.setProductPrice(cur.getInt(cur.getColumnIndex("productPrice")));
                    shoppingRecord.setProductInfo(productInfo);
                    list.add(shoppingRecord);
                } while (cur.moveToNext());
            }
            cur.close();
        }
        db.close();
        return list;
    }

    @SuppressLint("Range")
    public List<ShoppingRecord> getAllShoppingRecord(String account) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<ShoppingRecord> list = new ArrayList<>();
        String sql = "select * from shopping_car where account = '" + account + "'";
        Cursor cur = null;
        try {
            cur = db.rawQuery(sql, null);
        } catch (Exception e) {
            db.close();
            throw e;
        }
        if (cur != null) {
            if (cur.getCount() > 0) {
                cur.moveToFirst();
                do {
                    ShoppingRecord shoppingRecord = new ShoppingRecord();
                    shoppingRecord.setRecordId(cur.getInt(cur.getColumnIndex("shoppingRecordId")));
                    shoppingRecord.setAccount(cur.getString(cur.getColumnIndex("account")));
                    shoppingRecord.setProductAmount(cur.getInt(cur.getColumnIndex("amount")));
                    ProductInfo productInfo = new ProductInfo();
                    productInfo.setProductName(cur.getString(cur.getColumnIndex("productName")));
                    productInfo.setStoreName(cur.getString(cur.getColumnIndex("storeName")));
                    productInfo.setProductPrice(cur.getInt(cur.getColumnIndex("productPrice")));
                    shoppingRecord.setProductInfo(productInfo);
                    list.add(shoppingRecord);
                } while (cur.moveToNext());
            }
            cur.close();
        }
        db.close();
        return list;
    }

    private boolean doSQL(String sql) {
        try (SQLiteDatabase db = this.getReadableDatabase()) {
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
