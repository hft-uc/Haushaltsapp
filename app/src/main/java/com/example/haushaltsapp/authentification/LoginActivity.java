package com.example.haushaltsapp.authentification;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.haushaltsapp.MainActivity;
import com.example.haushaltsapp.R;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private Button btnRegistration;
    private EditText email;
    private EditText passwort;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.login);
        btnRegistration = findViewById(R.id.registrationFromLogin);
        email = findViewById(R.id.emailLogin);
        passwort = findViewById(R.id.password);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        btnLogin.setOnClickListener(view -> {
            String tempEmail = email.getText().toString().trim();
            String tempPass = passwort.getText().toString().trim();

            userViewModel.signIn(tempEmail, tempPass)
                .observe(this, success -> {
                    if (success) {
                        Toast.makeText(LoginActivity.this, "done", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                });
        });

        btnRegistration.setOnClickListener(view ->
            startActivity(new Intent(getApplicationContext(), RegistrationActivity.class))
        );
    }


}