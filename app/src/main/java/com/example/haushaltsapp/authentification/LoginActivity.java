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
    private UserViewModel userViewModel;

    @Override
    protected void onStart() {
        super.onStart();
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        if (userViewModel.alreadySignedIn()) {
            navigateToMain();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btnLogin = findViewById(R.id.login);
        Button btnRegistration = findViewById(R.id.registrationFromLogin);
        EditText email = findViewById(R.id.emailLogin);
        EditText passwort = findViewById(R.id.password);

        btnLogin.setOnClickListener(view -> {
            String tempEmail = email.getText().toString().trim();
            String tempPass = passwort.getText().toString().trim();

            userViewModel.signIn(tempEmail, tempPass)
                .observe(this, success -> {
                    if (Boolean.TRUE.equals(success)) {
                        Toast.makeText(LoginActivity.this, "done", Toast.LENGTH_SHORT).show();
                        navigateToMain();
                    } else {
                        Toast.makeText(LoginActivity.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                });
        });

        btnRegistration.setOnClickListener(view ->
            startActivity(new Intent(getApplicationContext(), RegistrationActivity.class))
        );
    }

    private void navigateToMain() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }


}