package com.example.haushaltsapp.chat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haushaltsapp.R;
import com.example.haushaltsapp.types.UserSummary;
import com.example.haushaltsapp.user.UserRepository;
import com.example.haushaltsapp.utils.FirestoreExtensionsKt;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {
    private RecyclerView chatRecycleView;
    private ChatUserListRecyclerViewAdapter userListRecyclerViewAdapter;
    private List<UserSummary> mUsers;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private List<String> userList;
    private final UserRepository userRepository;

    public ChatFragment() {
        userRepository = new UserRepository();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        chatRecycleView = view.findViewById(R.id.chat_recyclerview);
        chatRecycleView.setHasFixedSize(true);
        chatRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        userList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Chats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    if(chat.getSender().equals(firebaseUser.getUid())) {
                        if(!userList.contains(chat.getReceiver())) {
                            userList.add(chat.getReceiver());
                        }
                    }
                    if(chat.getReceiver().equals(firebaseUser.getUid())) {
                        if(!userList.contains(chat.getSender())) {
                            userList.add(chat.getSender());
                        }
                    }
                }
                readChats();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

    private void readChats() {
        mUsers =  new ArrayList<>();

        userRepository.getAllUsers().addSnapshotListener((value, error) -> {
            if (error == null) {
                mUsers.clear();
                List<UserSummary> users = FirestoreExtensionsKt.toObjectList(value.getDocuments(), UserSummary.class);
                for(UserSummary userSummary : users) {
                    for(String id : userList) {
                        if(id.equals(userSummary.getId())) {
                            if(mUsers.size() != 0) {
                                for(UserSummary userSummary1 : mUsers) {
                                    if(!userSummary.getId().equals(userSummary1.getId())) {
                                        mUsers.listIterator().add(userSummary);
                                    }
                                }
                            } else {
                                mUsers.add(userSummary);
                            }
                        }
                    }
                }
            } else {
                Log.w("TAG", "Failed to load all friends", error);
            }

            userListRecyclerViewAdapter = new ChatUserListRecyclerViewAdapter(mUsers, getContext());
            chatRecycleView.setAdapter(userListRecyclerViewAdapter);
        });
    }
}
