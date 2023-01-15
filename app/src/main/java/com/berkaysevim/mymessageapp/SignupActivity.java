package com.berkaysevim.mymessageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    EditText signupEmail, signupPass;
    Button signupEvent, loginActivityClick;

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupEmail = findViewById(R.id.signup_editTextTextEmailAddress);
        signupPass = findViewById(R.id.signup_editTextTextPassword);
        signupEvent = findViewById(R.id.signupClick);
        loginActivityClick = findViewById(R.id.signinActivityClick2);


        firebaseAuth = FirebaseAuth.getInstance();


        signupEvent.setOnClickListener(v->
            {
                String email = signupEmail.getText().toString();
                String password = signupPass.getText().toString();
                if (email.isEmpty() || password.isEmpty())
                    {
                        Toast.makeText(SignupActivity.this,"Boş Bırakmayınız",Toast.LENGTH_LONG ).show();
                    }
                if (password.length() < 6)
                   { Toast.makeText(SignupActivity.this,"Şifre Uzunluğunu 6 karakterden fazla yapınız ",Toast.LENGTH_LONG ).show();
                   }
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task ->
                        {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(this,"Kayıt Başarılı",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(this,LoginActivity.class));


                            }
                            else
                            {
                                Toast.makeText(this,"Kayıt Başarısız Tekrar Deneyiniz",Toast.LENGTH_LONG).show();
                                Log.i("information","Kayıt başarısız");
                            }
                        }

                        );

            });

        loginActivityClick.setOnClickListener(view ->
        {
            startActivity(new Intent(this,LoginActivity.class));
        });
    }
}