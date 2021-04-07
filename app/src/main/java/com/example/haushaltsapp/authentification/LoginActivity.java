package com.example.haushaltsapp.authentification;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.haushaltsapp.MainActivity;
import com.example.haushaltsapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private Button btnRegistration;
    private EditText email;
    private EditText passwort;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.login);
        btnRegistration = findViewById(R.id.registrationFromLogin);
        email = findViewById(R.id.emailLogin);
        passwort = findViewById(R.id.password);
        auth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(view -> {
            String tempEmail = email.getText().toString().trim();
            String tempPass = passwort.getText().toString().trim();

            auth.signInWithEmailAndPassword(tempEmail, tempPass).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    Toast.makeText(LoginActivity.this, "done", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    String fail = task.getException().toString();
                    Toast.makeText(LoginActivity.this, fail, Toast.LENGTH_SHORT).show();
                }

            });
        });

        btnRegistration.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class))
        );


    }
}