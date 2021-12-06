package com.example.cscb07_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class updateProductActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText addProductName, addPrice, addQuantity;
    private Button save;


    private Store store;
    private String username;
    private String storeName;
    private String ownerId;


    private Model model;
    private DatabaseReference storesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_products);
        model = Model.getInstance();
        Intent intent = getIntent();
        ownerId = intent.getStringExtra("ownerId");

        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(this);
        getStore();
    }
    //get the store by ownerId
    private void getStore() {
        model.getStoreByOwner(ownerId, (Store store) -> {

            this.store = store;

        });
    }

    public void updateItem() {

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        ownerId = intent.getStringExtra("ownerId");
        storeName = intent.getStringExtra("storeName");

        addProductName = (EditText) findViewById(R.id.addProductName);
        addPrice = (EditText) findViewById(R.id.addPrice);
        addQuantity = (EditText) findViewById(R.id.addQuantity);

        storesRef = FirebaseDatabase.getInstance().getReference("Stores");

        //create a new product
        Product product = new Product(
                addProductName.getText().toString().trim(),
                Double.parseDouble(addPrice.getText().toString()),
                Integer.parseInt(addQuantity.getText().toString())
        );
        String productName = addProductName.getText().toString().trim();
        Double productPrice = Double.parseDouble(addPrice.getText().toString());
        int productQuantity = Integer.parseInt(addQuantity.getText().toString());

        //update the product to firebase
        storesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {
                    String ownerID = ds.child("owner").getValue(String.class);

                    //String username1 = ds.child("username").getValue(String.class);

                    if (ownerID.equals(ownerId)) {

                        storesRef.child(storeName).child("products").child(productName).setValue(product);

                        Toast.makeText(updateProductActivity.this, "product has been saved", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(updateProductActivity.this, StoreOwnerPage.class);
                        intent.putExtra("ownerId", ownerId);
                        intent.putExtra("storeName", storeName);
                        startActivity(intent);
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //update the product to the products_inventory field of the store
        if(!store.products_inventory.contains(productName)){
            store.products_inventory.add(product);
        }
        else{
            for(Product p : store.products_inventory){
                if(p.name.equals(productName)){
                    p.inventory_quantity = productQuantity;
                    p.price = productPrice;
                }
            }
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.save) {
            updateItem();
        }
    }








}
