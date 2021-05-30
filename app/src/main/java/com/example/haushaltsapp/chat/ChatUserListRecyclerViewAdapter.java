package com.example.haushaltsapp.chat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.haushaltsapp.R;
import com.example.haushaltsapp.types.UserSummary;

import java.util.List;

public class ChatUserListRecyclerViewAdapter extends RecyclerView.Adapter<ChatUserListRecyclerViewAdapter.ViewHolder> {

    private final List<UserSummary> chatUsers;
    private final Context mContext;

    public ChatUserListRecyclerViewAdapter(List<UserSummary> chatUsers, Context mContext) {
        this.chatUsers = chatUsers;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.fragment_chat_user_item, parent, false);
        return new ChatUserListRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        UserSummary chatUser = chatUsers.get(position);
        // holder.mUserIdView.setText(chatUser.getId());
        holder.mChatUserView.setText(chatUser.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("userId", chatUser.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mUserIdView;
        public final TextView mChatUserView;

        public ViewHolder(View view) {
            super(view);
            mUserIdView = view.findViewById(R.id.user_id);
            mChatUserView = view.findViewById(R.id.chat_username);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mChatUserView.getText() + "'";
        }
    }
}