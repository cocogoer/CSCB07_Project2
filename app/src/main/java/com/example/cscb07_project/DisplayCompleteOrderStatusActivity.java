package com.example.cscb07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.List;

public class DisplayCompleteOrderStatusActivity extends AppCompatActivity {

    private String currentCustomerID;
    private String customerName;

    private ListView lvCompleted;
    private ArrayList<OrderInfo> completedOrders;

    private Button toPendingOrderPageBtn;
    private Button returnHomeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_completed_order);

        customerName = getIntent().getStringExtra("customerName");
        currentCustomerID = getIntent().getStringExtra("customerId");

        lvCompleted = (ListView) findViewById(R.id.lvCompletedOrders);

        toPendingOrderPageBtn = (Button) findViewById(R.id.pendingOrderBtn);
        toPendingOrderPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayCompleteOrderStatusActivity.this, DisplayPendingOrderActivity.class);
                intent.putExtra("customerId", currentCustomerID);
                startActivity(intent);
            }
        });

        returnHomeBtn = (Button) findViewById(R.id.completeReturnBtn);
        returnHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayCompleteOrderStatusActivity.this, CustomerPage.class);
                intent.putExtra("customerName", customerName);
                startActivity(intent);
            }
        });

        getOrder();
    }

    public void getOrder(){
        FirebaseDatabase.getInstance().getReference().child("Orders").orderByChild("customerId").
                equalTo(currentCustomerID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                completedOrders = new ArrayList<OrderInfo>();

                for (DataSnapshot orderSnapshot : snapshot.getChildren()) {
                    OrderInfo order = (OrderInfo) orderSnapshot.getValue(OrderInfo.class);
                    if (order.status.equals("completed")) {
                        completedOrders.add(order);
                    }
                }

                OrderStatusAdapter orderStatusAdapter = new OrderStatusAdapter
                        (DisplayCompleteOrderStatusActivity.this, R.layout.order_display_list_liew, completedOrders);
                lvCompleted.setAdapter(orderStatusAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}