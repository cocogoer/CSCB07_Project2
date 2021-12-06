package com.example.cscb07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class PopupActivity extends AppCompatActivity implements View.OnClickListener {

    private Button orderPopup;
    private Button cancelBtn;
    private Button confirmBtn;

    private OrderInfo order;
    private Customer customer;
    private String customerName;
    private String customerId;
    private String storeName;
    private String status = "pending";
    private ArrayList<Product> productArrayList;

    private Model model;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_order_popup);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase =FirebaseDatabase.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();


        dbRef = FirebaseDatabase.getInstance().getReference().child("Orders");
        String key = dbRef.push().getKey();

        Intent intent = getIntent();
        customerName = intent.getStringExtra("customerName");
        customerId = intent.getStringExtra("customerId");
//        storeName = intent.getStringExtra("storeName");

        //temporary for testing
        storeName = "S1";
        productArrayList = (ArrayList<Product>) intent.getSerializableExtra("order");

        order = new OrderInfo(storeName,status,customerName,customerId,productArrayList,key);

        if (order == null){
            order = new OrderInfo(storeName,status,customerName,customerId,productArrayList,key);
        }

        model = Model.getInstance();

        cancelBtn = (Button) findViewById(R.id.cancel_button);
        cancelBtn.setOnClickListener(this::onClick);

        confirmBtn = (Button) findViewById(R.id.confirm_button);
        confirmBtn.setOnClickListener(this);

//        orderPopup = (Button) findViewById(R.id.checkoutButton);
//        orderPopup.setOnClickListener(this::showPopup);
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//        int width = dm.widthPixels;
//        int height = dm.heightPixels;
//
//        getWindow().setLayout((int) (width*0.8), (int) (height*0.6));

    }

    public void cancel (){
        Intent intent = new Intent(PopupActivity.this, ShoppingCar.class);
        Toast.makeText(this,"Order Canceled", Toast.LENGTH_SHORT).show();
        startActivity(intent);

    }

    public void getUser(){
        model.getCustomer(customerId, (Customer customer) -> {
            this.customer = customer;
        });
    }

    public void updateConfirmOrderDataToDb (){

//        dbRef = FirebaseDatabase.getInstance().getReference().child("Order");
//        String key = dbRef.push().getKey();
//        OrderInfo order = new OrderInfo(storeName,status,customerName,productArrayList,key);
//        dbRef.child(key).setValue(order).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//                Toast.makeText(PopupActivity.this, "Data send", Toast.LENGTH_LONG).show();
//            }
//        });

//        orderDbRef = FirebaseDatabase.getInstance().getReference("Orders");

        model.postOrder(order, (OrderInfo order) -> {

            if (order == null) {
                Toast.makeText(this, "failed to create Order.", Toast.LENGTH_LONG).show();
                return;
            }


            Toast.makeText(PopupActivity.this, "Data send", Toast.LENGTH_LONG).show();
        });

    }

//    public void showPopup (){

//        Intent intent = new Intent(PopupActivity.this, )

//        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//        View popWindowView = layoutInflater.inflate(R.layout.confirm_order_popup, null);
//
//        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
//        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
//        boolean focusable = true;
//        final PopupWindow popup = new PopupWindow(popWindowView, width, height,focusable);
//
//        popup.showAtLocation(view, Gravity.CENTER, 0,0);
//
//        Button confirmBtn = popWindowView.findViewById(R.id.confirm_button);
//        confirmBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//
//        popWindowView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                popup.dismiss();
//                return true;
//            }
//        });

//    }

    @Override
    public void onClick (View view){
        switch (view.getId()) {
            case R.id.cancel_button:
                cancel();
                break;
            case R.id.confirm_button:
                updateConfirmOrderDataToDb();
                Intent intent = new Intent(PopupActivity.this, CustomerPage.class);
                intent.putExtra("customerName", customerName);
                startActivity(intent);
                break;
        }
    }
}