package com.example.cscb07_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cscb07_project.adapter.ProductListAdapter;
import com.example.cscb07_project.model.productDetail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {


    public ListView lv;

    // Arraylist for storing data
    public ArrayList<productDetail> ModelArrayList;

    //store the information that products from which store
    public String storeName;
    private Store store;
    public String ownerId;

    private Model model;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);
        storeName = getIntent().getStringExtra("storeName");
        ownerId = getIntent().getStringExtra("ownerId");
        model = Model.getInstance();



        lv = findViewById(R.id.productRv);



        getProduct();

    }

    public void getProduct(){


        DatabaseReference products = FirebaseDatabase.getInstance().
                getReference().child("Stores").child(storeName).child("products");
        products.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelArrayList = new ArrayList<productDetail>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Product p = (Product) ds.getValue(Product.class);
                    ModelArrayList.add(new productDetail(p.name,p.price,p.inventory_quantity));
                }
                ProductListAdapter productListAdapter = new ProductListAdapter
                        (ProductListActivity.this, R.layout.activity_product_detail, ModelArrayList);
                lv.setAdapter(productListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }



    public void back(View view){
        Intent intent = new Intent(ProductListActivity.this, StoreOwnerPage.class);
        intent.putExtra("ownerId", ownerId);
        intent.putExtra("storeName", storeName);
        startActivity(intent);
    }

}
