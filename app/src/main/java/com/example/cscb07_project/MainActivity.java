package com.example.cscb07_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void customerLoginMain(View view){
        startActivity(new Intent(this, CustomerLogin.class));

    }

    public void ownerLogin(View view){
        startActivity(new Intent(this, OwnerLogin.class));

    }
}