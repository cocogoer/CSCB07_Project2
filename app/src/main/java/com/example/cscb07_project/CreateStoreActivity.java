package com.example.cscb07_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateStoreActivity extends AppCompatActivity implements View.OnClickListener{

    private String ownerId;
    private String username;

    private EditText edTxtStoreName;
    private Button createStore;
    private Model model;

    private DatabaseReference storesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_store);

        edTxtStoreName = (EditText) findViewById(R.id.storeName);
        createStore = (Button) findViewById(R.id.CreateStore);
        createStore.setOnClickListener(this);

        ownerId = getIntent().getStringExtra("ownerId");
        username = getIntent().getStringExtra("username");
        model = Model.getInstance();
        storesRef = FirebaseDatabase.getInstance().getReference("Stores");
    }

    private void createStore() {
        String storeName = edTxtStoreName.getText().toString().trim().toLowerCase();
        Store store = new Store(storeName);
        store.owner = ownerId;

        model.postStore(store, (Boolean created) -> {
            if (!created) {
                Toast.makeText(CreateStoreActivity.this, "Failed to create a store!", Toast.LENGTH_LONG).show();
                return;
            }
            //storesRef.child("ownerId").setValue(ownerId);
            //storesRef.child("owner").setValue(username);
            Toast.makeText(CreateStoreActivity.this, "Store Created.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, StoreOwnerPage.class);
            intent.putExtra("username", username);
            intent.putExtra("ownerId", ownerId);

            startActivity(intent);
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.CreateStore:
                createStore();
                break;
        }
    }
}