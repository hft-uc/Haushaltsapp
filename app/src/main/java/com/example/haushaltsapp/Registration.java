package com.example.haushaltsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.haushaltsapp.types.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

public class Registration extends AppCompatActivity {

    EditText fullName, mEmail, passwort;
    Button mRegisterBtn, jumpBtn;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        fullName = findViewById(R.id.Name1);
        mEmail = findViewById(R.id.Email1);
        passwort = findViewById(R.id.Passwort1);
        mRegisterBtn = findViewById(R.id.registerbutton);
        auth = FirebaseAuth.getInstance();
        jumpBtn = findViewById(R.id.buttonJump);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        jumpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                String pass = passwort.getText().toString().trim();
                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(fullName.getText().toString(),email,FirebaseAuth.getInstance().getCurrentUser().getUid());

                            db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(user, SetOptions.merge());
                            Toast.makeText(Registration.this, "done", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            String fail =task.getException().toString();
                            Toast.makeText(Registration.this, fail, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}