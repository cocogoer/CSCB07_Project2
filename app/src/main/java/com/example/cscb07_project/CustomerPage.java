package com.example.cscb07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.PrimitiveIterator;

public class CustomerPage extends AppCompatActivity {
    private String username = "";
    private String customerId = "";
    private String storeName = "";
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private Button ShoppingCarBtn;
    private Button LogoutBtn;
    private Button viewOrderBtn;
    private Button ViewBtn;


    private Order order;

    public ArrayList<Product> orderProductList = new ArrayList<Product>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_page);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        customerId = intent.getStringExtra("customerId");

        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Store");

        DatabaseReference StoreName = ref.child("Store").child("storeName");
        storeName = StoreName.toString();

        TextView tv = (TextView)findViewById(R.id.welcome);
        tv.setText("Welcome " + username);

        ShoppingCarBtn = (Button) findViewById(R.id.ShoppingCarButton);
        ShoppingCarBtn.setOnClickListener(this::viewShoppingCar);

        LogoutBtn = (Button) findViewById(R.id.customerLogout);
        LogoutBtn.setOnClickListener(this::customerLogout);

        viewOrderBtn = (Button) findViewById(R.id.viewOrders);
        viewOrderBtn.setOnClickListener(this::viewAllOrders);

        ViewBtn = (Button) findViewById(R.id.viewProducts);
        ViewBtn.setOnClickListener(this::viewProducts);

    }

    public void viewShoppingCar (View view){

//        orderProductList.add(new Product("Apple", 2.3, 3));
//        orderProductList.add(new Product("Pear", 1.8, 1));
//        orderProductList.add(new Product("Banana", 3.0, 4));

        Intent intent = new Intent(CustomerPage.this, ShoppingCar.class);
        intent.putExtra("customerName", username);
        intent.putExtra("orderItem", orderProductList);
        intent.putExtra("orderStatus", "pending");
        intent.putExtra("storeName", storeName);
        intent.putExtra("customerId", customerId);

        startActivity(intent);
    }

    public void customerLogout(View view){
        startActivity (new Intent(CustomerPage.this, MainActivity.class));

    }

    public void viewProducts (View view){
        Intent intent = new Intent(CustomerPage.this, CustomStorePage.class);
        intent.putExtra("username", username);
        intent.putExtra("customerId", customerId);
        startActivity(intent);
    }

    public void viewAllOrders (View view){
        Intent intent = new Intent(CustomerPage.this, DisplayCompleteOrderStatusActivity.class);
        intent.putExtra("customerId", customerId);
        intent.putExtra("customerName", username);
        startActivity(intent);
    }
}
