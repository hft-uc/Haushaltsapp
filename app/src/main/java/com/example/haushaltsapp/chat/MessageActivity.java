package com.example.haushaltsapp.chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haushaltsapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageActivity extends AppCompatActivity {

    private ImageView profileImage;
    private TextView username;
    private ImageButton btnSend;
    private EditText txtSend;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore db;
    private Intent intent;
    private DatabaseReference mReference;

    private MessageAdapter messageAdapter;
    private List<Chat> mChats;
    private RecyclerView recyclerView;
    private ValueEventListener seenListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.message_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        profileImage = findViewById(R.id.profile_image);
        username = findViewById(R.id.chat_username1);
        btnSend = findViewById(R.id.btn_send);
        txtSend = findViewById(R.id.txt_send);
        intent = getIntent();
        String userId = intent.getStringExtra("userId");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();

        btnSend.setOnClickListener(v -> {
            String message = txtSend.getText().toString();
            if(!message.equals("")) {
                sendMessage(firebaseUser.getUid(), userId, message);
            } else {
                Toast.makeText(MessageActivity.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
            }
            txtSend.setText("");
        });


        DocumentReference docRef = db.collection("users").document(userId);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    username.setText(document.getData().get("name").toString());
                    profileImage.setImageResource(R.mipmap.ic_launcher_round);
                    Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                    readMessage(firebaseUser.getUid(), userId, null);
                } else {
                    Log.d("TAG", "No such document");
                }
            } else {
                Log.d("TAG", "get failed with ", task.getException());
            }
        });

        seenMessage(userId);
    }

    private void seenMessage(String userId) {
        mReference = FirebaseDatabase.getInstance().getReference("Chats");

        seenListener = mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    if(chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userId)) {
                        Map<String, Object> hashMap = new HashMap<>();
                        hashMap.put("isseen", true);
                        dataSnapshot.getRef().updateChildren(hashMap);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendMessage(String sender, String receiver, String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("isseen", false);
        reference.child("Chats").push().setValue(hashMap);
    }

    private void readMessage(final String myId, final String userId, final String imageUrl) {
        mChats = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mChats.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    if(chat.getReceiver().equals(myId) && chat.getSender().equals(userId)
                    || chat.getReceiver().equals(userId) && chat.getSender().equals(myId)) {
                        mChats.add(chat);
                    }
                }
                messageAdapter = new MessageAdapter(mChats, MessageActivity.this, imageUrl);
                recyclerView.setAdapter(messageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void status(String status) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", status);

        db.collection("users").document(firebaseUser.getUid()).update(hashMap);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        status("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        mReference.removeEventListener(seenListener);
        status("offline");
    }
}