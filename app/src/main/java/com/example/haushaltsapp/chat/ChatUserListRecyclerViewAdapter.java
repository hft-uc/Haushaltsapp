package com.example.haushaltsapp.chat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haushaltsapp.MessageActivity;
import com.example.haushaltsapp.R;
import com.example.haushaltsapp.authentification.AuthRepository;
import com.example.haushaltsapp.types.UserSummary;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ChatUserListRecyclerViewAdapter extends RecyclerView.Adapter<ChatUserListRecyclerViewAdapter.ViewHolder> {

    private final List<UserSummary> mUsers;
    private Context mContext;
    private boolean isChat;
    private String mLastMessage;
    private final AuthRepository authRepository;

    public ChatUserListRecyclerViewAdapter(List<UserSummary> mUsers, Context mContext, boolean isChat) {
        this.mUsers = mUsers;
        this.mContext = mContext;
        this.isChat = isChat;
        authRepository = new AuthRepository();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.fragment_chat_user_item, parent, false);
        return new ChatUserListRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        UserSummary user = mUsers.get(position);
        // holder.mUserIdView.setText(chatUser.getId());
        holder.txtUsername.setText(user.getName());

        if(user.getImageUrl().equals("default")) {
            holder.profileImage.setImageResource(R.mipmap.ic_launcher);
        } else {
            // Glide.with(getContext()).load(document.getData().get("imageUrl")).into(profileImage);
        }

        if(isChat) {
            lastMessage(user.getId(), holder.txtLastMessage);
        }

        if(isChat) {
            if(user.getStatus().equals("online")) {
                holder.imgOn.setVisibility(View.VISIBLE);
                holder.imgOff.setVisibility(View.GONE);
            } else {
                holder.imgOn.setVisibility(View.GONE);
                holder.imgOff.setVisibility(View.VISIBLE);
            }
        } else {
            holder.imgOn.setVisibility(View.GONE);
            holder.imgOff.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("userId", user.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtUsername;
        private final ImageView profileImage;
        private final ImageView imgOn;
        private final ImageView imgOff;
        private final TextView txtLastMessage;

        public ViewHolder(View view) {
            super(view);
            txtUsername = view.findViewById(R.id.username);
            profileImage = view.findViewById(R.id.profile_image);
            imgOn = view.findViewById(R.id.img_on);
            imgOff = view.findViewById(R.id.img_off);
            txtLastMessage = view.findViewById(R.id.last_message);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + txtUsername.getText() + "'";
        }
    }

    private void lastMessage(String userId, TextView txtLastMessage) {
        mLastMessage = "default";
        UserSummary currentUser = authRepository.getCurrentUser();
        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference("Chats");
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    if(chat.getReceiver().equals(currentUser.getId()) && chat.getSender().equals(userId)
                            || chat.getReceiver().equals(userId) && chat.getSender().equals(currentUser.getId())) {
                        mLastMessage = chat.getMessage();
                    }
                }
                switch (mLastMessage) {
                    case "default":
                        txtLastMessage.setText("No message");
                        break;
                    default:
                        txtLastMessage.setText(mLastMessage);
                        break;
                }
                mLastMessage = "default";
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}