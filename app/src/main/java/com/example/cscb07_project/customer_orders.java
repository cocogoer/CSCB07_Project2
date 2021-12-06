package com.example.cscb07_project;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class customer_orders extends AppCompatActivity {

    ListView listView_orderId;
    ListView listView_status;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_checkorders);
    }

    public void show_cus_order (View view){
        listView_orderId = (ListView)findViewById(R.id.orderid);
        listView_status = (ListView) findViewById(R.id.status);

        ArrayList<String> arrayList_orderId = new ArrayList<>();
        ArrayList<String> arrayList_status = new ArrayList<>();

        Intent intent = getIntent();
        String customerId = intent.getStringExtra("customerId");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Order");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot order_ids: snapshot.getChildren()){
                    String customer_id = order_ids.child("customerName").getValue(String.class);
                    if (customer_id.equals(customerId)){
                        arrayList_orderId.add(order_ids.toString());
                        arrayList_status.add(order_ids.child("status").getValue(String.class));
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        ArrayAdapter arrayAdapter_orderId = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList_orderId);
        ArrayAdapter arrayAdapter_status = new ArrayAdapter(this, android.R.layout.simple_list_item_2,arrayList_status);

        listView_orderId.setAdapter(arrayAdapter_orderId);
        listView_status.setAdapter(arrayAdapter_status);
        
    }
}
