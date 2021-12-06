package com.example.cscb07_project;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.cscb07_project.database.DataManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CustomStorePage extends AppCompatActivity {
    List<CustomStoreView> stores;
    FirebaseDatabase db;
    DatabaseReference StoreData;
    //DatabaseReference StoreOwner;
    private String username = "";
    private String customerId = "";
    private Button ShoppingCarBtn;
    private ListView listView;


    public void requestDataAsync(ValueEventListener listener) {
        //int amount = 0;
        db = FirebaseDatabase.getInstance();
        StoreData = db.getReference("Stores");
        StoreData.addValueEventListener(listener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_store_page);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        customerId = intent.getStringExtra("customerId");
        ShoppingCarBtn = (Button) findViewById(R.id.ShoppingCarButton);
        ShoppingCarBtn.setOnClickListener(this::viewShoppingCar);
        initView();
        DataManager.getInstance().init(getApplicationContext());
    }

    public void viewShoppingCar(View view) {


        Intent intent = new Intent(CustomStorePage.this, ShoppingCar.class);
//       intent.putExtra("items", orderProductList);
        startActivity(intent);
    }

    private void initView() {
        listView = findViewById(R.id.listViewStore);
        stores = new ArrayList<>();
        requestDataAsync(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String StoreName = ds.child("storeName").getValue(String.class);
                    String StoreOwner = ds.child("owner").getValue(String.class);
                    stores.add( new CustomStoreView(StoreName, StoreOwner));
                }
                if (stores.isEmpty()) {
                    Toast.makeText(CustomStorePage.this, "No data~", Toast.LENGTH_SHORT).show();
                    return;
                }
                flushList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(CustomStorePage.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initData() {
//        for (int i = 0; i < 10; i++) {
//            String name = "商铺" + i;
//            String owner = "店主" + i;
//            CustomStoreView store = new CustomStoreView(name, owner);
//            stores.add(store);
//        }
//        CustomStoreView store0 = new CustomStoreView("store", "ann");
//        stores.add(store0);
//        CustomStoreView store1 = new CustomStoreView("商铺1", "姓名1");
//        stores.add(store1);
//        CustomStoreView store2 = new CustomStoreView("store2", "ann2");
//        stores.add(store2);
//        CustomStoreView store3 = new CustomStoreView("storeeeeeeeeeeeeeeee", "annnnnnnnnnnn");
//        stores.add(store3);
//        CustomStoreView store4 = new CustomStoreView("store3", "ann3");
//        stores.add(store4);
//        CustomStoreView store5 = new CustomStoreView("store4", "ann4");
//        stores.add(store5);
//        CustomStoreView store6 = new CustomStoreView("store", "ann");
//        stores.add(store6);
//        CustomStoreView store7 = new CustomStoreView("store1", "ann1");
//        stores.add(store7);
//        CustomStoreView store8 = new CustomStoreView("store2", "ann2");
//        stores.add(store8);
//        CustomStoreView store9 = new CustomStoreView("storeeeeeeeeeeeeeeee", "annnnnnnnnnnn");
//        stores.add(store9);
//        CustomStoreView store10 = new CustomStoreView("store3", "ann3");
//        stores.add(store10);
//        CustomStoreView store11 = new CustomStoreView("store4", "ann4");
//        stores.add(store11);

    }

    private void flushList() {
        CustomStoreAdapter CustomStoreAdapter = new CustomStoreAdapter(CustomStorePage.this, stores);
        listView.setAdapter(CustomStoreAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            CustomStoreView store = stores.get(position);

            //把序列化的数据传递给商品列表界面
            Intent intent = new Intent(CustomStorePage.this, CustomProductPage.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(CustomProductPage.BUNDLE_ARG_STORE_DATA, store);
            intent.putExtras(bundle);
            intent.putExtras(bundle);

            startActivity(intent);
        });
    }
}

