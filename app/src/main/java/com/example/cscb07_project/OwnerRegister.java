package com.example.cscb07_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OwnerRegister extends AppCompatActivity {

    private EditText inputUsername;
    private EditText inputEmail;
    private EditText inputPassword;
    private EditText inputPhoneNum;
    // private boolean registered = false;

    FirebaseDatabase db;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_register);
    }

    public void ownerRegister(View view){

        inputUsername = findViewById(R.id.username);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        inputPhoneNum = findViewById(R.id.phoneNum);

        String username = inputUsername.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String phoneNum = inputPhoneNum.getText().toString();
        String ownerId = "owner" + phoneNum;

        boolean isValidInput = validateInput();


        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Owners");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean registered = false;
                for(DataSnapshot ds: snapshot.getChildren()){
                    String email1 = ds.child("email").getValue(String.class);
                    String ownerId1 = ds.child("ownerId").getValue(String.class);
                    if(email.equals(email1)){
                        Toast.makeText(OwnerRegister.this, "Email has been registered", Toast.LENGTH_SHORT).show();
                        registered = true;
                    }
                    else if(ownerId.equals(ownerId1)){
                        Toast.makeText(OwnerRegister.this, "Phone number has been registered", Toast.LENGTH_SHORT).show();
                        registered = true;
                    }
                }

                if(isValidInput && !registered){
                    Owner owner = new Owner(username, email, password, phoneNum);
                    ref.child(ownerId).setValue(owner);
                    Toast.makeText(OwnerRegister.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(OwnerRegister.this, OwnerLogin.class));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });


    }

    public boolean validateInput(){
        inputUsername = findViewById(R.id.username);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        inputPhoneNum = findViewById(R.id.phoneNum);

        String username = inputUsername.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String phoneNum = inputPhoneNum.getText().toString();
        boolean isValidUsername = !username.isEmpty();
        boolean isValidEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();;
        boolean isValidPassword = false;
        boolean isValidPhoneNum = false;
        boolean isValidInput = false;

        if(!isValidUsername){
            inputUsername.setError("Please enter your username");
            inputUsername.requestFocus();
        }

        if(email.isEmpty()){
            inputEmail.setError("Please enter your email");
            inputEmail.requestFocus();
        }
        else if(!isValidEmail){
            inputEmail.setError("Email is not valid");
            inputEmail.requestFocus();
        }

        Pattern p = Pattern.compile(".*[a-zA-Z].*");
        Matcher m = p.matcher(password);
        boolean containsLetter = m.matches();

        Pattern q = Pattern.compile(".*[0-9].*");
        Matcher n = q.matcher(password);
        boolean containsDigit = n.matches();
        
        Pattern pattern = Pattern.compile("^\\d{10}$");

        Matcher matcher = pattern.matcher(phoneNum);
        isValidPhoneNum = matcher.matches();
        if(!isValidPhoneNum){
            inputPhoneNum.setError("Phone number is not valid");
            inputPhoneNum.requestFocus();
            return false;
        }

        if(password.isEmpty()){
            inputPassword.setError("Please enter your password");
            inputPassword.requestFocus();
        }
        else if(password.length() < 12){
            inputPassword.setError("Password must contain at least 12 characters");
            inputPassword.requestFocus();
        }
        else if(!containsDigit){
            inputPassword.setError("Password must contain at least one digit");
            inputPassword.requestFocus();
        }
        else if(!containsLetter){
            inputPassword.setError("Password must contain at least one letter");
            inputPassword.requestFocus();
        }

        isValidPassword = !password.isEmpty() && ! (password.length() < 12) && containsDigit && containsLetter;
        return isValidUsername && isValidEmail && isValidPassword && isValidPhoneNum;
    }


}
