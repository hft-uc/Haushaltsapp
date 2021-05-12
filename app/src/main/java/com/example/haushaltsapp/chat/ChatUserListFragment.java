package com.example.haushaltsapp.chat;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haushaltsapp.R;
import com.example.haushaltsapp.types.UserSummary;
import com.example.haushaltsapp.user.UserRepository;
import com.example.haushaltsapp.utils.FirestoreExtensionsKt;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class ChatUserListFragment extends Fragment {

    private RecyclerView recyclerView;
    private final UserRepository userRepository;
    private List<UserSummary> chatUsers;
    private ChatUserListRecyclerViewAdapter recyclerViewAdapter;


    public ChatUserListFragment() {
        userRepository =  new UserRepository();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_user_list, container, false);
        recyclerView = view.findViewById(R.id.chat_user_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        chatUsers = new ArrayList<>();

        readChatUsers();

        return view;
    }

    private void readChatUsers() {
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userRepository.getAllUsers().addSnapshotListener((value, error) -> {
            if (error == null) {
                List<UserSummary> users = FirestoreExtensionsKt.toObjectList(value.getDocuments(), UserSummary.class);
                for(UserSummary userSummary : users) {
                    if(userSummary.getId().equals(currentUser.getUid())) {
                        continue;
                    }
                    chatUsers.add(userSummary);
                }
            } else {
                Log.w("TAG", "Failed to load all friends", error);
            }
            recyclerViewAdapter = new ChatUserListRecyclerViewAdapter(chatUsers, getContext());
            recyclerView.setAdapter(recyclerViewAdapter);
        });
    }

    /**
    private List<ChatUser> readChatUsers() {
        List<ChatUser> chatUsers = new ArrayList<>();
        chatUsers.add(new ChatUser(1, "Salma"));
        chatUsers.add(new ChatUser(2, "Marie"));

        return chatUsers;
    }
     */
}