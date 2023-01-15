package com.berkaysevim.mymessageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(this,LoginActivity.class));

        }


    }


    public void loginActivityClick(View view) {

        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
    }

    public void signupActivityClick(View view) {
        startActivity(new Intent(this,SignupActivity.class));
    }

}