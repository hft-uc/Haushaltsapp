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

public class RegistrationActivity extends AppCompatActivity {

    private EditText fullName;
    private EditText mEmail;
    private EditText passwort;
    private Button mRegisterBtn;

    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        fullName = findViewById(R.id.Name1);
        mEmail = findViewById(R.id.Email1);
        passwort = findViewById(R.id.Passwort1);
        mRegisterBtn = findViewById(R.id.register_button);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        mRegisterBtn.setOnClickListener(view -> {
            String email = mEmail.getText().toString().trim();
            String pass = passwort.getText().toString().trim();

            authViewModel.register(email, pass, fullName.getText().toString())
                .observe(this, success -> {
                    if (success) {
                        Toast.makeText(RegistrationActivity.this, "done", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else {
                        Toast.makeText(RegistrationActivity.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                });
        });
    }
}