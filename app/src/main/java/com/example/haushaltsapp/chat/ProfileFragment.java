package com.example.haushaltsapp.chat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.haushaltsapp.R;
import com.example.haushaltsapp.user.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private CircleImageView profileImage;
    private TextView username;
    private FirebaseUser currentUser;
    private FirebaseFirestore db;

    public ProfileFragment() {
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImage = view.findViewById(R.id.profile_image);
        username = view.findViewById(R.id.username);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();


        DocumentReference docRef = db.collection("users").document(currentUser.getUid());
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    username.setText(document.getData().get("name").toString());
                    if(document.getData().get("imageUrl") != null && document.getData().get("imageUrl").equals("default")) {
                        profileImage.setImageResource(R.mipmap.ic_launcher);
                    } else {
                        // Glide.with(getContext()).load(document.getData().get("imageUrl")).into(profileImage);
                        profileImage.setImageResource(R.mipmap.ic_launcher);
                    }
                    Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                } else {
                    Log.d("TAG", "No such document");
                }
            } else {
                Log.d("TAG", "get failed with ", task.getException());
            }
        });

        return view;
    }
}