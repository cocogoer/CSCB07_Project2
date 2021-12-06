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

public class CustomerRegister extends AppCompatActivity {

    private EditText inputUsername;
    private EditText inputEmail;
    private EditText inputPassword;
    private EditText inputPhoneNum;


    FirebaseDatabase db;
    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);
    }

    public void customerRegister(View view){

        inputUsername = findViewById(R.id.username);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        inputPhoneNum = findViewById(R.id.phoneNum);

        String username = inputUsername.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String phoneNum = inputPhoneNum.getText().toString();
        String customerId = "customer" + phoneNum;

        boolean isValidInput = validateInput();

        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Customers");
        
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean registered = false;
                for(DataSnapshot ds: snapshot.getChildren()){
                    String email1 = ds.child("email").getValue(String.class);
                    String customerId1 = ds.child("customerId").getValue(String.class);
                    if(email.equals(email1)){
                        inputEmail.setError("Email has been registered");
                    
                        registered = true;
                    }
                    else if(customerId.equals(customerId1)){
                        inputPhoneNum.setError("Phone number has been registered");
                        registered = true;
                    }
                }
                if(isValidInput && !registered){
                    Customer customer = new Customer(username, email, password, phoneNum);
                    ref.child(customerId).setValue(customer);
                    Toast.makeText(CustomerRegister.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CustomerRegister.this, CustomerLogin.class));
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
            return false;
        }

        if(email.isEmpty()){
            inputEmail.setError("Please enter your email");
            inputEmail.requestFocus();
            return false;
        }
        else if(!isValidEmail){
            inputEmail.setError("Email is not valid");
            inputEmail.requestFocus();
            return false;
        }
        
        Pattern pattern = Pattern.compile("^\\d{10}$");

        Matcher matcher = pattern.matcher(phoneNum);
        isValidPhoneNum = matcher.matches();
        if(!isValidPhoneNum){
            inputPhoneNum.setError("Phone number is not valid");
            inputPhoneNum.requestFocus();
            return false;
        }

        Pattern p = Pattern.compile(".*[a-zA-Z].*");
        Matcher m = p.matcher(password);
        boolean containsLetter = m.matches();

        Pattern q = Pattern.compile(".*[0-9].*");
        Matcher n = q.matcher(password);
        boolean containsDigit = n.matches();

        if(password.isEmpty()){
            inputPassword.setError("Please enter your password");
            inputPassword.requestFocus();
            return false;
        }
        else if(password.length() < 12){
            inputPassword.setError("Password must contain at least 12 characters");
            inputPassword.requestFocus();
            return false;
        }
        else if(!containsDigit){
            inputPassword.setError("Password must contain at least one digit");
            inputPassword.requestFocus();
            return false;
        }
        else if(!containsLetter){
            inputPassword.setError("Password must contain at least one letter");
            inputPassword.requestFocus();
            return false;
        }
        

        isValidPassword = !password.isEmpty() && ! (password.length() < 12) && containsDigit && containsLetter;
        
        

        return isValidUsername && isValidEmail && isValidPassword && isValidPhoneNum;
    }

}
