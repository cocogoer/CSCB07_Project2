package com.example.cscb07_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerLogin extends AppCompatActivity {

    private FirebaseDatabase db;
    private DatabaseReference ref;
    private EditText inputEmail;
    private EditText inputPassword;
    private int tolerance = 3;
    private boolean found = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);
    }

    public void onCustomerRegister(View view){
        startActivity(new Intent(this, CustomerRegister.class));
    }

    public void customerLogin(View view){
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);

        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Customers");

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
                        String customerId = ds.child("customerId").getValue(String.class);
                        String username = ds.child("username").getValue(String.class);
                        String locked = ds.child("locked").getValue(String.class);
                        
                        if(locked.equals("true")){
                            inputEmail.setError("Your account has been locked");
                            return;
                        }
                        else if(p.equals(password)){
                            Toast.makeText(CustomerLogin.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                            ref.child(customerId).child("login").setValue("true");
                            Intent intent = new Intent(CustomerLogin.this, CustomerPage.class);
                            intent.putExtra("username", username);
                            intent.putExtra("customerId", customerId);
                            startActivity(intent);
                            return;
                        }
                        else{
                            tolerance = tolerance - 1;
                            if(tolerance > 0){
                                inputEmail.setText("");
                                inputPassword.setText("");
                                Toast.makeText(CustomerLogin.this, "Incorrect password. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                            if(tolerance == 0){
                                ref.child(customerId).child("locked").setValue("true");
                                Toast.makeText(CustomerLogin.this, "Your account will be locked", Toast.LENGTH_SHORT).show();
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

    public void write(String username, String field, String data){
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Customers");
        ref.child(username).child(field).setValue(data);
    }


}
