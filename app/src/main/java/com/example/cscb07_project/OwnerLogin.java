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

public class OwnerLogin extends AppCompatActivity {

    private FirebaseDatabase db;
    private DatabaseReference ref;
    private EditText inputEmail;
    private EditText inputPassword;
    private int tolerance = 3;
    private boolean found = false;

    private Button loginbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_login);

        loginbtn = (Button) findViewById(R.id.ownerLogin);
        loginbtn.setOnClickListener(this::ownerLogin);
    }

    public void onOwnerRegister(View view){
        startActivity(new Intent(this, OwnerRegister.class));
    }

    public void ownerLogin(View view){
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);

        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Owners");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    String e = ds.child("email").getValue(String.class);
                    System.out.println(e);
                    String p = ds.child("password").getValue(String.class);
                    if(e.equals(email)){
                        found = true;
                        // System.out.println("found email");
                        String phoneNum = ds.child("phoneNum").getValue(String.class);
                        String username = ds.child("username").getValue(String.class);
                        String ownerId = "owner" + phoneNum;
                        String locked = ds.child("locked").getValue(String.class);
                        if(locked.equals("true")){
                            inputEmail.setError("Your account has been locked");
                            return;
                        }
                        else if(p.equals(password)){
                            Toast.makeText(OwnerLogin.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                            write(ownerId, "login", "true");
                            Intent intent = new Intent(OwnerLogin.this, StoreOwnerPage.class);
                            intent.putExtra("username", username);
                            intent.putExtra("ownerId", ownerId);
                            startActivity(intent);
                            return;
                        }
                        else{
                            tolerance = tolerance - 1;
                            if(tolerance > 0){
                                inputEmail.setText("");
                                inputPassword.setText("");
                                Toast.makeText(OwnerLogin.this, "Incorrect password. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                            if(tolerance == 0){
                                write(ownerId, "locked", "true");
                                inputEmail.setError("Your account will be locked");
                                
                                return;
                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void write(String userId, String field, String data){
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Owners");
        ref.child(userId).child(field).setValue(data);
    }
}
