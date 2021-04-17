package com.example.haushaltsapp.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haushaltsapp.R;
import com.example.haushaltsapp.types.UserSummary;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import javax.annotation.Nonnull;

public class UserRecyclerViewAdapter
    extends FirestoreRecyclerAdapter<UserSummary, UserRecyclerViewAdapter.ViewHolder> {

    public UserRecyclerViewAdapter(@NonNull FirestoreRecyclerOptions<UserSummary> options) {
        super(options);
    }

    @Nonnull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.fragment_user_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull UserSummary model) {
        holder.item = model;
        holder.contentview.setText(model.getName());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView contentview;
        public UserSummary item;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            contentview = view.findViewById(R.id.user_list_item_name);
        }

        @Nonnull
        @Override
        public String toString() {
            return super.toString() + " '" + contentview.getText() + "'";
        }
    }
}