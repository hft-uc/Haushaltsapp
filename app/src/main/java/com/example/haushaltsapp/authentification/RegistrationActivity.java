package com.example.haushaltsapp.authentification;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.haushaltsapp.MainActivity;
import com.example.haushaltsapp.R;
import com.example.haushaltsapp.types.UserDetail;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

public class RegistrationActivity extends AppCompatActivity {

    private EditText fullName;
    private EditText mEmail;
    private EditText passwort;
    private Button mRegisterBtn;
    private Button jumpBtn;
    private FirebaseAuth auth;

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
        jumpBtn.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), MainActivity.class)));
        mRegisterBtn.setOnClickListener(view -> {
            String email = mEmail.getText().toString().trim();
            String pass = passwort.getText().toString().trim();
            auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    UserDetail userDetail = new UserDetail(fullName.getText().toString(), email, FirebaseAuth.getInstance().getCurrentUser().getUid());

                    db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(userDetail, SetOptions.merge());
                    Toast.makeText(RegistrationActivity.this, "done", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    String fail = task.getException().toString();
                    Toast.makeText(RegistrationActivity.this, fail, Toast.LENGTH_SHORT).show();
                }
            });

        });
    }
}