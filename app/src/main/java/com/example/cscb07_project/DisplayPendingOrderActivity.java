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

import java.util.ArrayList;


public class DisplayPendingOrderActivity extends AppCompatActivity {

    private String currentCustomerID;
    private String customerName;

    private ListView lvPending;
    private ArrayList<OrderInfo> pendingOrders;

    private Button toCompleteOrderPageBtn;
    private Button returnHomeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_pending_order);

        currentCustomerID = getIntent().getStringExtra("customerId");
        customerName = getIntent().getStringExtra("customerName");

        lvPending = (ListView) findViewById(R.id.lvPendingOrders);

        toCompleteOrderPageBtn = (Button) findViewById(R.id.completedOrderBtn);
        toCompleteOrderPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayPendingOrderActivity.this, DisplayCompleteOrderStatusActivity.class);
                intent.putExtra("customerId", currentCustomerID);
                startActivity(intent);
            }
        });

        returnHomeBtn = (Button) findViewById(R.id.pendingReturnBtn);
        returnHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayPendingOrderActivity.this, CustomerPage.class);
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
                pendingOrders = new ArrayList<OrderInfo>();

                for (DataSnapshot orderSnapshot : snapshot.getChildren()) {
                    OrderInfo order = (OrderInfo) orderSnapshot.getValue(OrderInfo.class);
                    if (order.status.equals("pending")) {
                        pendingOrders.add(order);
                    }
                }

                OrderStatusAdapter orderStatusAdapter = new OrderStatusAdapter
                        (DisplayPendingOrderActivity.this, R.layout.order_display_list_liew, pendingOrders);
                lvPending.setAdapter(orderStatusAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}