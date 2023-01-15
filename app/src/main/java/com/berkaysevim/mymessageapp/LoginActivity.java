package com.berkaysevim.mymessageapp;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText signinEmail, signinPass;
    Button loginEvent, signupActivityClick;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signinEmail = findViewById(R.id.signin_editTextTextEmailAddress);
        signinPass = findViewById(R.id.signin_editTextTextPassword);
        loginEvent = findViewById(R.id.loginClick);
        signupActivityClick = findViewById(R.id.signinActivityClick2);

        firebaseAuth = FirebaseAuth.getInstance();

        loginEvent.setOnClickListener( view ->
                {
                    String email = signinEmail.getText().toString();
                    String password = signinPass.getText().toString();
                        if (email.isEmpty() || password.isEmpty())
                        {
                            Toast.makeText(this,"Boş Bırakmayınız",Toast.LENGTH_SHORT).show();


                        }
                        if (password.length() < 6)
                        { Toast.makeText(this,"Şifre Uzunluğunu 6 karakterden fazla yapınız "
                                ,Toast.LENGTH_SHORT ).show();
                        }
                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task ->
                            {
                                if (task.isSuccessful())
                                {

                                    Toast.makeText(LoginActivity.this,"Giriş Başarılı"
                                            ,Toast.LENGTH_SHORT).show();

                                    Log.i("information","Giriş başarılı");
                                    startActivity(new Intent(this,MainActivity.class));






                                }
                                else
                                {
                                    Toast.makeText(this,"Giriş başarız Tekrar Deneyiniz"
                                            ,Toast.LENGTH_SHORT).show();
                                    Log.i("information","Giriş başarız");



                                }
                            }

                    );
                }


        );

        signupActivityClick.setOnClickListener( view ->
                {
                    startActivity(new Intent(this, SignupActivity.class));
                }
        );



    }
}