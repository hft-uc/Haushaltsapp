package com.example.haushaltsapp.chat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.haushaltsapp.MessageActivity;
import com.example.haushaltsapp.R;
import com.example.haushaltsapp.types.UserSummary;

import java.util.List;

public class ChatUserListRecyclerViewAdapter extends RecyclerView.Adapter<ChatUserListRecyclerViewAdapter.ViewHolder> {

    private final List<UserSummary> mUsers;
    private Context mContext;
    private boolean isChat;

    public ChatUserListRecyclerViewAdapter(List<UserSummary> mUsers, Context mContext, boolean isChat) {
        this.mUsers = mUsers;
        this.mContext = mContext;
        this.isChat = isChat;
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

        public ViewHolder(View view) {
            super(view);
            txtUsername = view.findViewById(R.id.username);
            profileImage = view.findViewById(R.id.profile_image);
            imgOn = view.findViewById(R.id.img_on);
            imgOff = view.findViewById(R.id.img_off);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + txtUsername.getText() + "'";
        }
    }
}